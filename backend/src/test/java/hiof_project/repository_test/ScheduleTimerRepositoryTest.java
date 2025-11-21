package hiof_project.repository_test;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.ScheduleTimer;
import hiof_project.infrastructure.adapters.db.ScheduleTimerRepositorySQL;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTimerRepositorySQLTest {

    private Connection connection;
    private ScheduleTimerRepositorySQL scheduleTimerRepository;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:testdb_scheduletimer;",
                "sa",
                ""
        );

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS ScheduleTimers");
            stmt.execute("DROP TABLE IF EXISTS Schedules");

            // Enkelt Schedules-oppsett for å tilfredsstille FK
            stmt.execute("""
                CREATE TABLE Schedules (
                    schedule_id INT PRIMARY KEY,
                    defined_date DATE NOT NULL
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

            // Sett inn én schedule slik at schedule_id=1 finnes
            stmt.execute("""
                INSERT INTO Schedules (schedule_id, defined_date)
                VALUES (1, DATE '2025-11-19')
            """);
        }

        scheduleTimerRepository = new ScheduleTimerRepositorySQL(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    //Lage en ankomst/avreise og hente den direkte via ID
    @Test
    void createAndGetScheduleTimerId() throws RepositoryException {
        ScheduleTimer timer = new ScheduleTimer(LocalTime.of(7, 0), LocalTime.of(7, 15));
        timer.setScheduleId(1);  // FK til Schedules
        timer.setSequence(1);    // Første stopp i rekkefølge

        scheduleTimerRepository.createScheduleTimer(timer);

        // siden schedule_timer_id er AUTO_INCREMENT, antar vi at første rad får id = 1
        Optional<ScheduleTimer> fromOptDB = scheduleTimerRepository.getByScheduleTimerId(1);
        assertTrue(fromOptDB.isPresent(), "ScheduleTimer ID 1 skal finnes i databasen.");

        ScheduleTimer fromDB = fromOptDB.get();
        assertEquals(LocalTime.of(7, 0), fromDB.getArrival());
        assertEquals(LocalTime.of(7, 15), fromDB.getDeparture());
    }

    //Teste getTimersByScheduleId: alle ankomst/avreiser til en bestemt tidsplan
    @Test
    void getTimersByScheduleIdTest() throws RepositoryException {
        // Første timer
        ScheduleTimer t1 = new ScheduleTimer(LocalTime.of(8, 0), LocalTime.of(8, 5));
        t1.setScheduleId(1);
        t1.setSequence(1);
        scheduleTimerRepository.createScheduleTimer(t1);

        // Andre timer
        ScheduleTimer t2 = new ScheduleTimer(LocalTime.of(8, 10), LocalTime.of(8, 20));
        t2.setScheduleId(1);
        t2.setSequence(2);
        scheduleTimerRepository.createScheduleTimer(t2);

        ArrayList<ScheduleTimer> list = scheduleTimerRepository.getTimersByScheduleId(1);

        assertEquals(2, list.size(), "Det skal finnes 2 ScheduleTimers for schedule_id = 1.");

        assertTrue(list.stream().anyMatch(t ->
                t.getArrival().equals(LocalTime.of(8, 0)) &&
                        t.getDeparture().equals(LocalTime.of(8, 5))
        ));

        assertTrue(list.stream().anyMatch(t ->
                t.getArrival().equals(LocalTime.of(8, 10)) &&
                        t.getDeparture().equals(LocalTime.of(8, 20))
        ));
    }
}
