package hiof_project.repository_test;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Stop;
import hiof_project.infrastructure.adapters.db.StopRepositorySQL;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StopRepositorySQLTest {

    private Connection connection;
    private StopRepositorySQL stopRepository;

    @BeforeEach
    void setUp() throws Exception {
        //H2 test in-memory DB (kun for test)
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:testdb;",
                "sa",
                ""
        );

        //Lag Buses-tabellen slik oppsett i SQL
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Stops");

            stmt.execute("""
                CREATE TABLE Stops (
                    stop_id INT PRIMARY KEY,
                    stop_name NVARCHAR(75) NOT NULL,
                    distance_to_next_km DECIMAL(5,2) NULL,
                    time_to_next_stop INT NULL,
                    desc_next_stop NVARCHAR(125) NULL,
                    route_id INT NOT NULL
                );
            """);
        }

        //Repository med denne connection
        stopRepository = new StopRepositorySQL(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    @Test
    void createAndGetStopId() throws RepositoryException {
        Stop remmen = new Stop(1, "Høgskolen i Østfold",
                0, 0, "N/A");

        stopRepository.createStop(remmen, 1);
        Optional<Stop> fromDB = stopRepository.getByStopId(1);

        assertTrue(fromDB.isPresent());
        assertEquals("Høgskolen i Østfold", fromDB.get().getStopName());
        assertEquals(0, fromDB.get().getDistanceInKm());
        assertEquals(0, fromDB.get().getTimeToNextStop());
        assertEquals("N/A", fromDB.get().getDescriptionNextStop());
    }

    @Test
    void saveStopThenUpdate() throws RepositoryException {
        Stop amfiSarp = new Stop(1, "Amfiborg Sarpsborg",
                0, 0, "N/A");
        stopRepository.saveStop(amfiSarp, 1); // SAVE STOP

        Stop updatedStop = new Stop(1, "Værste Fredrikstad",
                0, 0, "N/A");
        stopRepository.updateStop(updatedStop,1 ); // UPDATE STOP

        Optional<Stop> fromDB = stopRepository.getByStopId(1);

        assertTrue(fromDB.isPresent());
        assertEquals("Værste Fredrikstad", fromDB.get().getStopName());
        assertEquals(0, fromDB.get().getDistanceInKm());
        assertEquals(0, fromDB.get().getTimeToNextStop());
        assertEquals("N/A", fromDB.get().getDescriptionNextStop());
    }

    @Test
    void getAllStopsTest() throws RepositoryException {
        Stop tista= new Stop(1, "Tista Halden",
                5, 10, "Stopper ved P-plassen");
        Stop sentrum = new Stop(2, "Sentrum Halden",
                10, 20, "Stopper ved Høgskolen i Østfold");
        Stop remmen = new Stop(3, "Høgskolen i Østfold",
                0, 0, "Ingen neste destinasjon");

        stopRepository.createStop(tista, 1);
        stopRepository.createStop(sentrum, 1);
        stopRepository.createStop(remmen, 1);

        ArrayList<Stop> allStops = stopRepository.getAllStops();

        assertEquals(3, allStops.size());
        // enkel ekstra sjekk
        assertTrue(allStops.stream().anyMatch(b -> b.getStopName().equals("Tista Halden")));
        assertTrue(allStops.stream().anyMatch(b -> b.getStopName().equals("Sentrum Halden")));
        assertTrue(allStops.stream().anyMatch(b -> b.getStopName().equals("Høgskolen i Østfold")));
    }

    @Test
    void deleteStopIdTest() throws RepositoryException {
        Stop festningen = new Stop(1, "Halden Festning");
        stopRepository.createStop(festningen, 1);

        Optional<Stop> beforeDelete = stopRepository.getByStopId(1); //Sjekk om det finnes før sletting
        assertTrue(beforeDelete.isPresent());

        stopRepository.deleteStopId(1);

        Optional<Stop> afterDelete = stopRepository.getByStopId(1); //Sjekk om det er slettet fra DB
        assertTrue(afterDelete.isEmpty());
    }
}
