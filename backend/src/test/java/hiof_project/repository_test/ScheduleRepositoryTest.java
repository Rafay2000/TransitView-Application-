package hiof_project.repository_test;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Schedule;
import hiof_project.domain.model.transport_system.ScheduleTimer;
import hiof_project.infrastructure.adapters.db.ScheduleRepositorySQL;
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

class ScheduleRepositorySQLTest {

    private Connection connection;
    private ScheduleRepositorySQL scheduleRepository;
    private ScheduleTimerRepositorySQL scheduleTimerRepository;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:testdb_schedule;",
                "sa",
                ""
        );

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS ScheduleTimers");
            stmt.execute("DROP TABLE IF EXISTS Schedules");

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
                    CONSTRAINT FK_Timer_On_Schedule FOREIGN KEY (schedule_id) REFERENCES Schedules(schedule_id)
                );
            """);
        }

        scheduleTimerRepository = new ScheduleTimerRepositorySQL(connection);
        scheduleRepository = new ScheduleRepositorySQL(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        connection.close();
    }

    //Lag en tidsplan men ankomst og avreiser tider i tidsplanen
    @Test
    void createAndGetScheduleWithHoursMinutes() throws RepositoryException {
        ArrayList<ScheduleTimer> timersTest = new ArrayList<>();
        timersTest.add(new ScheduleTimer(LocalTime.of(7, 0), LocalTime.of(7, 30))); //ankomst/avreise
        timersTest.add(new ScheduleTimer(LocalTime.of(9, 0), LocalTime.of(9, 45))); //ankomst/avreise

        Schedule scheduleTest = new Schedule(1, timersTest, LocalDate.of(2025, 11, 19));
        scheduleRepository.createSchedule(scheduleTest); //CREATE CRUD

        Optional<Schedule> fromOptDB = scheduleRepository.getByScheduleId(1);
        assertTrue(fromOptDB.isPresent(), "Schedule ID 1: finnes i databasen med ankomst/avreiser.");

        Schedule fromDB = fromOptDB.get();
        assertEquals(LocalDate.of(2025, 11, 19), fromDB.getDefinedDate());

        //Sjekk at begge ankomst/avreiser tidene er i databasen
        ArrayList<ScheduleTimer> fromTimersDB = fromDB.getScheduleTimer();
        assertEquals(2, fromTimersDB.size());

        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getArrival().equals(LocalTime.of(7, 0))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getDeparture().equals(LocalTime.of(7, 30))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getArrival().equals(LocalTime.of(9, 0))));
        assertTrue(fromTimersDB.stream().anyMatch(t -> t.getDeparture().equals(LocalTime.of(9, 45))));
    }

    //Test for å hente alle tidsplaner med tilhørende ankomst/avreiser og dato
    @Test
    void getAllSchedulesTest() throws RepositoryException {
        //Schedule objekt 1 med timers
        ArrayList<ScheduleTimer> timersTest1 = new ArrayList<>();
        timersTest1.add(new ScheduleTimer(LocalTime.of(5, 25), LocalTime.of(6, 15)));
        timersTest1.add(new ScheduleTimer(LocalTime.of(8, 0), LocalTime.of(8, 50)));
        Schedule scheduleTest1 = new Schedule(1, timersTest1, LocalDate.of(2025, 11, 18));
        scheduleRepository.createSchedule(scheduleTest1);

        //Schedule objekt 2 med timers
        ArrayList<ScheduleTimer> timersTest2 = new ArrayList<>();
        timersTest2.add(new ScheduleTimer(LocalTime.of(5, 45), LocalTime.of(6, 55)));
        timersTest2.add(new ScheduleTimer(LocalTime.of(9, 5), LocalTime.of(9, 20)));
        Schedule scheduleTest2 = new Schedule(2, timersTest2, LocalDate.of(2025, 11, 19));
        scheduleRepository.createSchedule(scheduleTest2);

        ArrayList<Schedule> allSchedulesTest = scheduleRepository.getAllSchedules();
        assertEquals(2, allSchedulesTest.size(), "Fant 2 schedules i databasen.");

        //Ekstra enkel sjekk på dato + antall timers per schedule
        Schedule schedule1 = allSchedulesTest.stream()
                .filter(s -> s.getScheduleId() == 1)
                .findFirst()
                .orElseThrow();
        assertEquals(LocalDate.of(2025, 11, 18), schedule1.getDefinedDate());
        assertEquals(2, schedule1.getScheduleTimer().size());

        Schedule schedule2 = allSchedulesTest.stream()
                .filter(s -> s.getScheduleId() == 2)
                .findFirst()
                .orElseThrow();
        assertEquals(LocalDate.of(2025, 11, 19), schedule2.getDefinedDate());
        assertEquals(2, schedule2.getScheduleTimer().size());
    }
}
