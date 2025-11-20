package hiof_project.repository_test;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.RealtimeScheduling;
import hiof_project.infrastructure.adapters.db.RealtimeSchedulingRepositorySQL;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RealtimeSchedulingRepositorySQLTest {

    private Connection connection;
    private RealtimeSchedulingRepositorySQL realtimeRepository;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:testdb_realtime;DB_CLOSE_DELAY=-1;",
                "sa",
                ""
        );

        try (Statement stmt = connection.createStatement()) {
            // Dropp i riktig rekkefølge (FK avhenger av Trips/Stops)
            stmt.execute("DROP TABLE IF EXISTS RealtimeScheduling");
            stmt.execute("DROP TABLE IF EXISTS Trips");
            stmt.execute("DROP TABLE IF EXISTS Stops");

            // Minimal Trips-tabell for FK
            stmt.execute("""
                CREATE TABLE Trips (
                    trip_id INT PRIMARY KEY
                );
            """);

            // Minimal Stops-tabell for FK
            stmt.execute("""
                CREATE TABLE Stops (
                    stop_id INT PRIMARY KEY,
                    stop_name NVARCHAR(75) NOT NULL
                );
            """);

            // RealtimeScheduling-tabell (tilpasset H2)
            stmt.execute("""
                CREATE TABLE RealtimeScheduling (
                    realtime_id INT AUTO_INCREMENT PRIMARY KEY,
                    trip_id INT NOT NULL,
                    stop_id INT NOT NULL,
                    updated_arrival TIME NULL,
                    updated_departure TIME NULL,
                    status NVARCHAR(50) NULL,
                    CONSTRAINT FK_RealTimeOn_Trip FOREIGN KEY (trip_id) REFERENCES Trips(trip_id),
                    CONSTRAINT FK_RealTimeOn_Stop FOREIGN KEY (stop_id) REFERENCES Stops(stop_id)
                );
            """);

            // Sett inn noen trips og stops slik at FK-ene funker
            stmt.execute("INSERT INTO Trips (trip_id) VALUES (1)");
            stmt.execute("INSERT INTO Trips (trip_id) VALUES (2)");

            stmt.execute("INSERT INTO Stops (stop_id, stop_name) VALUES (1, 'Remmen Høgskole')");
            stmt.execute("INSERT INTO Stops (stop_id, stop_name) VALUES (2, 'Fredrikstad Busstasjon')");
            stmt.execute("INSERT INTO Stops (stop_id, stop_name) VALUES (3, 'Greåker')");
        }

        realtimeRepository = new RealtimeSchedulingRepositorySQL(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    // 1) CREATE + GET BY ID
    @Test
    void createAndGetUpdatedTimeById() throws RepositoryException {
        // Antar konstruktør: (tripId, stopId, updatedArrival, updatedDeparture, status)
        RealtimeScheduling update = new RealtimeScheduling(
                1,
                1,
                LocalTime.of(8, 5),
                LocalTime.of(8, 10),
                "Delayed 5 min"
        );

        realtimeRepository.createUpdatedTime(update);

        Optional<RealtimeScheduling> fromOpt = realtimeRepository.getByUpdatedTimeId(1);
        assertTrue(fromOpt.isPresent(), "RealtimeScheduling med ID 1 skal finnes i databasen.");

        RealtimeScheduling fromDB = fromOpt.get();
        assertEquals(1, fromDB.getTripId());
        assertEquals(1, fromDB.getStopId());
        assertEquals(LocalTime.of(8, 5), fromDB.getUpdatedArrival());
        assertEquals(LocalTime.of(8, 10), fromDB.getUpdatedDeparture());
        assertEquals("Delayed 5 min", fromDB.getStatus());
    }

    // 2) FINN EN OPPFØRING BASERT PÅ TRIP + STOP
    @Test
    void findUpdatedScheduleByTripAndStop() throws RepositoryException {
        RealtimeScheduling u1 = new RealtimeScheduling(
                1, 1,
                LocalTime.of(8, 0),
                LocalTime.of(8, 5),
                "On time"
        );
        RealtimeScheduling u2 = new RealtimeScheduling(
                1, 2,
                LocalTime.of(8, 15),
                LocalTime.of(8, 20),
                "Delayed 10 min"
        );
        RealtimeScheduling u3 = new RealtimeScheduling(
                2, 2,
                LocalTime.of(9, 0),
                LocalTime.of(9, 5),
                "Cancelled time"
        );

        realtimeRepository.createUpdatedTime(u1);
        realtimeRepository.createUpdatedTime(u2);
        realtimeRepository.createUpdatedTime(u3);

        Optional<RealtimeScheduling> fromOpt =
                realtimeRepository.findUpdatedScheduleByTripAndStop(1, 2);

        assertTrue(fromOpt.isPresent(), "Skal finne realtime-oppføring for trip 1, stop 2.");

        RealtimeScheduling fromDB = fromOpt.get();
        assertEquals(1, fromDB.getTripId());
        assertEquals(2, fromDB.getStopId());
        assertEquals(LocalTime.of(8, 15), fromDB.getUpdatedArrival());
        assertEquals(LocalTime.of(8, 20), fromDB.getUpdatedDeparture());
        assertEquals("Delayed 10 min", fromDB.getStatus());
    }

    // 3) FINN ALLE OPPFØRINGER FOR EN TRIP
    @Test
    void findAllUpdatedScheduleByTrip() throws RepositoryException {
        RealtimeScheduling u1 = new RealtimeScheduling(
                1, 1,
                LocalTime.of(8, 0),
                LocalTime.of(8, 5),
                "On time"
        );
        RealtimeScheduling u2 = new RealtimeScheduling(
                1, 2,
                LocalTime.of(8, 15),
                LocalTime.of(8, 20),
                "Delayed"
        );
        RealtimeScheduling u3 = new RealtimeScheduling(
                1, 3,
                LocalTime.of(8, 30),
                LocalTime.of(8, 35),
                "On time"
        );
        RealtimeScheduling u4 = new RealtimeScheduling(
                2, 2,
                LocalTime.of(9, 0),
                LocalTime.of(9, 5),
                "Cancelled"
        );

        realtimeRepository.createUpdatedTime(u1);
        realtimeRepository.createUpdatedTime(u2);
        realtimeRepository.createUpdatedTime(u3);
        realtimeRepository.createUpdatedTime(u4);

        ArrayList<RealtimeScheduling> list =
                realtimeRepository.findAllUpdatedScheduleByTrip(1);

        assertEquals(3, list.size(), "Trip 1 skal ha 3 realtime-oppføringer.");

        assertTrue(list.stream().anyMatch(r ->
                r.getTripId() == 1 &&
                        r.getStopId() == 1 &&
                        "On time".equals(r.getStatus())
        ));

        assertTrue(list.stream().anyMatch(r ->
                r.getTripId() == 1 &&
                        r.getStopId() == 2 &&
                        "Delayed".equals(r.getStatus())
        ));

        assertTrue(list.stream().anyMatch(r ->
                r.getTripId() == 1 &&
                        r.getStopId() == 3 &&
                        "On time".equals(r.getStatus())
        ));
    }

    // 4) GET ALL + DELETE
    @Test
    void getAllAndDeleteUpdatedTime() throws RepositoryException {
        RealtimeScheduling u1 = new RealtimeScheduling(
                1, 1,
                LocalTime.of(8, 0),
                LocalTime.of(8, 5),
                "On time"
        );
        RealtimeScheduling u2 = new RealtimeScheduling(
                1, 2,
                LocalTime.of(8, 15),
                LocalTime.of(8, 20),
                "Delayed"
        );

        realtimeRepository.createUpdatedTime(u1);
        realtimeRepository.createUpdatedTime(u2);

        ArrayList<RealtimeScheduling> allBefore = realtimeRepository.getAllUpdatedTime();
        assertEquals(2, allBefore.size(), "Skal være 2 realtime-oppføringer før sletting.");

        // Sletter første rad (realtime_id = 1, siden AUTO_INCREMENT starter på 1)
        realtimeRepository.deleteUpdatedTimeId(1);

        ArrayList<RealtimeScheduling> allAfter = realtimeRepository.getAllUpdatedTime();
        assertEquals(1, allAfter.size(), "Skal være 1 realtime-oppføring etter sletting.");

        // Sjekk at den som er igjen IKKE er knyttet til realtime_id 1 hvis du har feltet i domeneklassen
        // Hvis domeneklassen din ikke har realtimeId-felt, kan du droppe ekstra assert på det.
    }
}

