package hiof_project.repository_test;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.*;
import hiof_project.infrastructure.adapters.db.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalTime;
import java.time.LocalDate;

class TripRepositorySQLTest {

    private Connection connection;
    private TripRepositorySQL tripRepository;
    private RouteRepositorySQL routeRepository;
    private StopRepositorySQL stopRepository;
    private ScheduleRepositorySQL scheduleRepository;
    private BusRepositorySQL busRepository;
    private ScheduleTimerRepositorySQL scheduleTimerRepository;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:testdb;",
                "sa",
                "");

        //Lage alle tabellene for det Trip trenger
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS ScheduleTimers");
            stmt.execute("DROP TABLE IF EXISTS Trips");
            stmt.execute("DROP TABLE IF EXISTS Stops");
            stmt.execute("DROP TABLE IF EXISTS Buses");
            stmt.execute("DROP TABLE IF EXISTS Schedules");
            stmt.execute("DROP TABLE IF EXISTS Routes");

            //Må følges i riktig struktur fra SQL tabellen
            stmt.execute("""
                CREATE TABLE Routes (
                    route_id INT PRIMARY KEY,
                    route_name NVARCHAR(55) NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE Buses (
                    bus_id INT PRIMARY KEY,
                    bus_name NVARCHAR(50) NOT NULL,
                    bus_type NVARCHAR(50) NOT NULL,
                    capacity INT NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE Schedules (
                    schedule_id INT PRIMARY KEY,
                    defined_date DATE NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE Stops (
                    stop_id INT PRIMARY KEY,
                    stop_name NVARCHAR(75) NOT NULL,
                    distance_to_next_km DECIMAL(5,2),
                    time_to_next_stop INT,
                    desc_next_stop NVARCHAR(125),
                    route_id INT NOT NULL,
                    CONSTRAINT FK_Stops_On_Routes FOREIGN KEY (route_id)
                        REFERENCES Routes(route_id)
                );
            """);

            stmt.execute("""
                CREATE TABLE ScheduleTimers (
                    schedule_timer_id INT AUTO_INCREMENT PRIMARY KEY,
                    schedule_id INT NOT NULL,
                    arrival TIME NOT NULL,
                    departure TIME NOT NULL,
                    sequence INT NOT NULL,
                    CONSTRAINT FK_Timer_On_Schedule FOREIGN KEY (schedule_id)
                        REFERENCES Schedules(schedule_id)
                );
            """);

            stmt.execute("""
                CREATE TABLE Trips (
                    trip_id INT PRIMARY KEY,
                    route_id INT NOT NULL,
                    schedule_id INT NOT NULL,
                    bus_id INT NOT NULL,
                    CONSTRAINT FK_Route_On_Trip FOREIGN KEY (route_id) REFERENCES Routes(route_id),
                    CONSTRAINT FK_Schedule_On_Trip FOREIGN KEY (schedule_id) REFERENCES Schedules(schedule_id),
                    CONSTRAINT FK_Bus_On_Trip FOREIGN KEY (bus_id) REFERENCES Buses(bus_id)
                );
            """);
        }

        // Initialize repositories
        routeRepository = new RouteRepositorySQL(connection);
        stopRepository = new StopRepositorySQL(connection);
        scheduleTimerRepository = new ScheduleTimerRepositorySQL(connection);
        scheduleRepository = new ScheduleRepositorySQL(connection);
        busRepository = new BusRepositorySQL(connection);
        tripRepository = new TripRepositorySQL(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    //Teste fullstendige turen med rute, bussholdeplasser, tider og type buss
    @Test
    void createFullTripTest() throws RepositoryException {
        //Opprett Bus objekt først
        Bus testBus = new Bus(1, "R4", "Bybuss - Elektrisk", 45);
        busRepository.createBus(testBus);

        //Opprette først bussholdeplass objekter
        Stop fredrikstad = new Stop(1, "Fredrikstad Busstasjon", 3.5,
                10, "Stopper ved Rema 1000");
        Stop glemmen = new Stop(2, "Glemmen Videregående", 5.0,
                15, "Stopper ved store parkeringsplassen");
        Stop ostfoldhallen = new Stop(3, "Østfoldhallen", 12.0,
                17, "Stopper ved hovedveien fra skolen");
        Stop greaaker = new Stop(4, "Greåker Videregående", 23.0,
                31, "Siste stopp på Fredrikstad Bussterminal");

        //Deretter legg til alle bussholdeplassene i en ArrayList
        ArrayList<Stop> testStops = new ArrayList<>();
        testStops.add(fredrikstad);
        testStops.add(glemmen);
        testStops.add(ostfoldhallen);
        testStops.add(greaaker);

        //Opprett rute ved å bruke CreateRoute metoden og legg til alle bussholdeplassene fra ArrayListen
        Route testRoute = new Route(1,"FR-GR03", testStops);
        routeRepository.createRoute(testRoute);

        //Opprette Schedule med ankomst og avreiser tider fra ScheduleTimers
        ArrayList<ScheduleTimer> timersTest = new ArrayList<>();
        timersTest.add(new ScheduleTimer(LocalTime.of(8, 50), LocalTime.of(9, 0)));
        timersTest.add(new ScheduleTimer(LocalTime.of(9, 10), LocalTime.of(9, 25)));
        timersTest.add(new ScheduleTimer(LocalTime.of(9, 40), LocalTime.of(9, 50)));
        timersTest.add(new ScheduleTimer(LocalTime.of(10, 7), LocalTime.of(10, 14)));

        Schedule testSchedule = new Schedule(1, timersTest, LocalDate.of(2025, 11, 19));
        scheduleRepository.createSchedule(testSchedule);

        //Opprett Trip med Route, Schedule, og Bus
        Trip testTrip = new Trip(1, testRoute, testSchedule, testBus);
        tripRepository.createTrip(testTrip);

        //Hent Trip fra databasen og verifiser at det er lagret korrekt
        Optional<Trip> fromOptDB = tripRepository.getByTripId(1);
        assertTrue(fromOptDB.isPresent(), "Trip ID 1: finnes i databasen.");

        Trip fromTripDB = fromOptDB.get();

        //Sjekk at data i Trip er korrekt
        assertEquals(1, fromTripDB.getTripId());
        assertEquals("FR-GR03", fromTripDB.getRoute().getRouteName());
        assertEquals("R4", fromTripDB.getBus().getVehicleName());
        assertEquals(LocalDate.of(2025, 11, 19), fromTripDB.getSchedule().getDefinedDate());

        //Sjekke at ScheduleTimers er lagret og hentet korrekt, må finne 4 ankomst/avreiser hver
        ArrayList<ScheduleTimer> fromTimersDB = fromTripDB.getSchedule().getScheduleTimer();
        assertEquals(4, fromTimersDB.size());

        // Verifiser at tidene for hvert stopp er korrekt
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getArrival().equals(LocalTime.of(8, 50))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getDeparture().equals(LocalTime.of(9, 0))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getArrival().equals(LocalTime.of(9, 10))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getDeparture().equals(LocalTime.of(9, 25))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getArrival().equals(LocalTime.of(9, 40))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getDeparture().equals(LocalTime.of(9, 50))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getArrival().equals(LocalTime.of(10, 7))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getDeparture().equals(LocalTime.of(10, 14))));
    }
}
