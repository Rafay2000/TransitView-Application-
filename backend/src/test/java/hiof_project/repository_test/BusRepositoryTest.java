package hiof_project.repository_test;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Bus;
import hiof_project.infrastructure.adapters.db.BusRepositorySQL;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BusRepositorySQLTest {

    private Connection connection;
    private BusRepositorySQL busRepository;

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
            stmt.execute("DROP TABLE IF EXISTS Buses");

            stmt.execute("""
                CREATE TABLE Buses (
                    bus_id INT PRIMARY KEY,
                    bus_name NVARCHAR(50) NOT NULL,
                    bus_type NVARCHAR(50) NOT NULL,
                    capacity INT NOT NULL
                );
            """);
        }

        //Repository med denne connection
        busRepository = new BusRepositorySQL(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    @Test
    void createAndGetBusId() throws RepositoryException {
        Bus bus = new Bus(1, "FLY-B1", "Flybuss - Bensin", 60);

        busRepository.createBus(bus);
        Optional<Bus> fromDB = busRepository.getByBusId(1);

        assertTrue(fromDB.isPresent());
        assertEquals("FLY-B1", fromDB.get().getVehicleName());
        assertEquals("Flybuss - Bensin", fromDB.get().getVehicleType());
        assertEquals(60, fromDB.get().getCapacity());
    }

    @Test
    void saveBusThenUpdate() throws RepositoryException {
        Bus bus = new Bus(1, "BY-RX1", "Bybuss - Elektrisk", 30);
        busRepository.saveBus(bus); // create

        Bus updatedBus = new Bus(1, "BY-RX2", "Bybuss - Diesel", 40);
        busRepository.updateBus(updatedBus); // update

        Optional<Bus> fromDB = busRepository.getByBusId(1);

        assertTrue(fromDB.isPresent());
        assertEquals("BY-RX2", fromDB.get().getVehicleName());
        assertEquals("Bybuss - Diesel", fromDB.get().getVehicleType());
        assertEquals(40, fromDB.get().getCapacity());
    }

    @Test
    void getAllBusesTest() throws RepositoryException {
        Bus bus1 = new Bus(1, "BY-N1", "Bybuss - Diesel", 40);
        Bus bus2 = new Bus(2, "FLY-RX5", "Flybuss - Bensin", 60);
        Bus bus3 = new Bus(3, "TURIST-T1", "Turistbuss - Elektrisk", 35);

        busRepository.createBus(bus1);
        busRepository.createBus(bus2);
        busRepository.createBus(bus3);

        ArrayList<Bus> allBuses = busRepository.getAllBuses();

        assertEquals(3, allBuses.size());
        // enkel ekstra sjekk
        assertTrue(allBuses.stream().anyMatch(b -> b.getVehicleName().equals("BY-N1")));
        assertTrue(allBuses.stream().anyMatch(b -> b.getVehicleName().equals("FLY-RX5")));
        assertTrue(allBuses.stream().anyMatch(b -> b.getVehicleName().equals("TURIST-T1")));
    }

    @Test
    void deleteBusIdTest() throws RepositoryException {
        Bus bus = new Bus(1, "NIGHT-01", "Nattbuss - Diesel", 35);
        busRepository.createBus(bus);

        Optional<Bus> beforeDelete = busRepository.getByBusId(1); //Sjekk om det finnes før sletting
        assertTrue(beforeDelete.isPresent());

        busRepository.deleteBusId(1);

        Optional<Bus> afterDelete = busRepository.getByBusId(1); //Sjekk om det er slettet fra DB
        assertTrue(afterDelete.isEmpty());
    }
}
