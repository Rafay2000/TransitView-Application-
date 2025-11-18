package hiof_project.infrastructure.adapters.db;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Schedule;
import hiof_project.domain.model.transport_system.ScheduleTimer;
import hiof_project.ports.out.ScheduleRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class ScheduleRepositorySQL implements ScheduleRepository {

    private Connection connection;
    private final ScheduleTimerRepositorySQL scheduleTimerRepository;

    public ScheduleRepositorySQL(Connection connection) {
        this.connection = connection;
        this.scheduleTimerRepository = new ScheduleTimerRepositorySQL(connection);
    }

    // Opprette tidsplan
    @Override
    public void createSchedule(Schedule schedule) throws RepositoryException {
        String sql = "INSERT INTO Schedules (schedule_id, defined_date) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, schedule.getScheduleId());
            preparedStatement.setDate(2, Date.valueOf(schedule.getDefinedDate()));
            preparedStatement.executeUpdate();

            // Lagre alle ankomst/avgang (ScheduleTimer) til tilhørende tidsplan
            ArrayList<ScheduleTimer> timers = schedule.getScheduleTimer();
            if (timers != null) {
                for (int i = 0; i < timers.size(); i++) {
                    ScheduleTimer t = timers.get(i);
                    t.setScheduleId(schedule.getScheduleId());
                    t.setSequence(i + 1); // 1, 2, 3, ...
                    scheduleTimerRepository.saveScheduleTimer(t);
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not create schedule in database", e);
        }
    }

    // Optional-style effektiv måte: hvis ID finnes i DB. Så update, ellers create
    @Override
    public void saveSchedule(Schedule schedule) throws RepositoryException {
        Optional<Schedule> existing = getByScheduleId(schedule.getScheduleId());

        if (existing.isPresent()) {
            updateSchedule(schedule);
        } else {
            createSchedule(schedule);
        }
    }

    // Oppdater tidsplan (definert dato + tilhørende timers)
    @Override
    public void updateSchedule(Schedule schedule) throws RepositoryException {
        String sql = "UPDATE Schedules SET defined_date = ? WHERE schedule_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, Date.valueOf(schedule.getDefinedDate()));
            preparedStatement.setInt(2, schedule.getScheduleId());
            preparedStatement.executeUpdate();

            // Samme strategi som i save: slett alle timers og legg inn på nytt
            String deleteTimersSql = "DELETE FROM ScheduleTimers WHERE schedule_id = ?";
            try (PreparedStatement deleteTimersStmt = connection.prepareStatement(deleteTimersSql)) {
                deleteTimersStmt.setInt(1, schedule.getScheduleId());
                deleteTimersStmt.executeUpdate();
            }

            ArrayList<ScheduleTimer> timers = schedule.getScheduleTimer();
            if (timers != null) {
                for (int i = 0; i < timers.size(); i++) {
                    ScheduleTimer t = timers.get(i);
                    t.setScheduleId(schedule.getScheduleId());
                    t.setSequence(i + 1); // 1, 2, 3, ...
                    scheduleTimerRepository.saveScheduleTimer(t);
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not update schedule in database", e);
        }
    }

    // Slett tidsplan basert på IDen
    @Override
    public void deleteScheduleId(int scheduleId) throws RepositoryException {
        // Slett først alle ScheduleTimers pga. foreign key
        String deleteTimersSql = "DELETE FROM ScheduleTimers WHERE schedule_id = ?";

        try (PreparedStatement deleteTimersStmt = connection.prepareStatement(deleteTimersSql)) {
            deleteTimersStmt.setInt(1, scheduleId);
            deleteTimersStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not delete schedule timers for schedule ID " + scheduleId, e);
        }

        String sql = "DELETE FROM Schedules WHERE schedule_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scheduleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not delete schedule with matching ID " + scheduleId, e);
        }
    }

    // Hent tidsplan basert på IDen
    @Override
    public Optional<Schedule> getByScheduleId(int scheduleId) throws RepositoryException {
        String sql = "SELECT schedule_id, defined_date FROM Schedules WHERE schedule_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scheduleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("schedule_id");
                LocalDate definedDate = resultSet.getDate("defined_date").toLocalDate();

                // Hent alle timers for denne schedule
                ArrayList<ScheduleTimer> timers = scheduleTimerRepository.getTimersByScheduleId(id);

                Schedule schedule = new Schedule(id, timers, definedDate);
                return Optional.of(schedule);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve schedule with ID " + scheduleId, e);
        }
    }

    // Hent alle tidsplaner fra db
    @Override
    public ArrayList<Schedule> getAllSchedules() throws RepositoryException {
        String sql = "SELECT schedule_id, defined_date FROM Schedules";
        ArrayList<Schedule> schedules = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("schedule_id");
                LocalDate definedDate = resultSet.getDate("defined_date").toLocalDate();

                ArrayList<ScheduleTimer> timers = scheduleTimerRepository.getTimersByScheduleId(id);

                Schedule schedule = new Schedule(id, timers, definedDate);
                schedules.add(schedule);
            }

            return schedules;
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve schedules from database", e);
        }
    }
}
