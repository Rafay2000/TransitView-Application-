package hiof_project.repository_test;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Route;
import hiof_project.domain.model.transport_system.Stop;
import hiof_project.infrastructure.adapters.db.RouteRepositorySQL;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RouteRepositorySQLTest {

    private Connection connection;
    private RouteRepositorySQL routeRepository;

    @BeforeEach
    void setUp() throws Exception {
        //H2 test in-memory DB (kun for test)
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:testdb;",
                "sa",
                ""
        );

        //Lag Routes og Stops-tabellen slik oppsett i SQL
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Routes");
            stmt.execute("DROP TABLE IF EXISTS Stops");

            stmt.execute("""
                CREATE TABLE Routes (
                    route_id INT PRIMARY KEY,
                    route_name NVARCHAR(55) NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE Stops (
                    stop_id INT PRIMARY KEY,
                    stop_name NVARCHAR(75) NOT NULL,
                    distance_to_next_km DECIMAL(5,2) NULL,
                    time_to_next_stop INT NULL,
                    desc_next_stop NVARCHAR(125) NULL,
                    route_id INT NOT NULL,
                    FOREIGN KEY (route_id) REFERENCES Routes(route_id)
                );
            """);
        }

        //Repository med denne connection
        routeRepository = new RouteRepositorySQL(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    @Test
    void createAndGetRouteId() throws RepositoryException {
        Route route = new Route(1, "Halden");

        routeRepository.createRoute(route);
        Optional<Route> fromDB = routeRepository.getByRouteId(1);

        assertTrue(fromDB.isPresent());
        assertEquals("Halden", fromDB.get().getRouteName());
    }

    @Test
    void saveRouteThenUpdate() throws RepositoryException {
        Route route = new Route(1, "Halden");
        routeRepository.saveRoute(route); // SAVE STOP

        Route updatedRoute = new Route(1, "Fredrikstad");
        routeRepository.updateRoute(updatedRoute); // UPDATE STOP

        Optional<Route> fromDB = routeRepository.getByRouteId(1);

        assertTrue(fromDB.isPresent());
        assertEquals("Fredrikstad", fromDB.get().getRouteName());
    }

    //Ekstra test som skal lage og finne rute med bussholdeplasser
    @Test
    void createAndGetRouteWithStops() throws RepositoryException {
        Stop stop1 = new Stop(1, "Sentrum Halden",
                5, 10, "Stopper ved Høgskolen i Østfold");
        Stop stop2 = new Stop(2, "Høgskolen i Østfold",
                0, 0, "Ingen neste destinasjon");

        ArrayList<Stop> stops = new ArrayList<>();
        stops.add(stop1);
        stops.add(stop2);

        // Oppretter en rute med stoppene
        Route route = new Route(1, "Remmen-Høgskole", stops);
        routeRepository.createRoute(route); // Lagre ruta i DB

        Optional<Route> fromDB = routeRepository.getByRouteId(1);

        assertTrue(fromDB.isPresent());
        assertEquals("Remmen-Høgskole", fromDB.get().getRouteName());

        //Hent bussholdeplassene og verifiser at de ble lagret med ruten
        assertEquals(2, fromDB.get().getStops().size());
        assertTrue(fromDB.get().getStops().stream().anyMatch(s -> s.getStopName().equals("Sentrum Halden")));
        assertTrue(fromDB.get().getStops().stream().anyMatch(s -> s.getStopName().equals("Høgskolen i Østfold")));
    }

    @Test
    void saveRouteThenUpdateWithStops() throws RepositoryException {
        Stop stop1 = new Stop(1, "Sentrum Halden",
                5, 10, "Stopper ved Høgskolen i Østfold");
        Stop stop2 = new Stop(2, "Høgskolen i Østfold",
                0, 0, "Ingen neste destinasjon");

        ArrayList<Stop> stops = new ArrayList<>();
        stops.add(stop1);
        stops.add(stop2);

        // Oppretter og lagrer en rute med stoppene
        Route route = new Route(1, "Remmen-Høgskole", stops);
        routeRepository.saveRoute(route); // Lagre ruta

        // Oppdaterer ruten med nytt navn og nye bussholdeplasser
        Stop updatedStop1 = new Stop(1, "Tista Senter",
                10, 20, "Stopper ved Halden Festningen");
        Stop updatedStop2 = new Stop(2, "Halden Festning",
                0, 0, "Siste stopp");

        ArrayList<Stop> updatedStops = new ArrayList<>();
        updatedStops.add(updatedStop1);
        updatedStops.add(updatedStop2);

        Route updatedRoute = new Route(1, "Tista-Festning", updatedStops);
        routeRepository.updateRoute(updatedRoute);

        //Hent oppdatert rute fra databasen
        Optional<Route> fromDB = routeRepository.getByRouteId(1);

        assertTrue(fromDB.isPresent());
        assertEquals("Tista-Festning", fromDB.get().getRouteName());

        //Hent stoppene og verifiser at de ble oppdatert
        assertEquals(2, fromDB.get().getStops().size());
        assertTrue(fromDB.get().getStops().stream().anyMatch(s -> s.getStopName().equals("Tista Senter")));
        assertTrue(fromDB.get().getStops().stream().anyMatch(s -> s.getStopName().equals("Halden Festning")));
    }

    @Test
    void getAllRoutesTest() throws RepositoryException {
        Route halden = new Route(1, "Halden");
        Route fredrikstad = new Route(2, "Fredrikstad");
        Route moss = new Route(3, "Moss");

        routeRepository.createRoute(halden);
        routeRepository.createRoute(fredrikstad);
        routeRepository.createRoute(moss);

        ArrayList<Route> allRoutes = routeRepository.getAllRoutes();

        assertEquals(3, allRoutes.size());
        // enkel ekstra sjekk
        assertTrue(allRoutes.stream().anyMatch(b -> b.getRouteName().equals("Halden")));
        assertTrue(allRoutes.stream().anyMatch(b -> b.getRouteName().equals("Fredrikstad")));
        assertTrue(allRoutes.stream().anyMatch(b -> b.getRouteName().equals("Moss")));
    }

    @Test
    void deleteRouteIdTest() throws RepositoryException {
        Route route = new Route(1, "Oslo");
        routeRepository.createRoute(route);

        Optional<Route> beforeDelete = routeRepository.getByRouteId(1); //Sjekk om det finnes før sletting
        assertTrue(beforeDelete.isPresent());

        routeRepository.deleteRouteId(1);

        Optional<Route> afterDelete = routeRepository.getByRouteId(1); //Sjekk om det er slettet fra DB
        assertTrue(afterDelete.isEmpty());
    }
}
