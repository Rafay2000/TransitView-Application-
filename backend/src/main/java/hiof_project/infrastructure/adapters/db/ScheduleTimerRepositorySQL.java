package hiof_project.infrastructure.adapters.db;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.ScheduleTimer;
import hiof_project.ports.out.ScheduleTimerRepository;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class ScheduleTimerRepositorySQL implements ScheduleTimerRepository {

    private Connection connection;

    public ScheduleTimerRepositorySQL(Connection connection) {
        this.connection = connection;
    }

    // Oppretter ankomst/avgang for et gitt schedule + rekkefølge
    // Oppretter ny timer – forventer at scheduleId og sequence er satt på objektet
    @Override
    public void createScheduleTimer(ScheduleTimer scheduleTimer) throws RepositoryException {
        String sql = "INSERT INTO ScheduleTimers (schedule_id, arrival, departure, sequence) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scheduleTimer.getScheduleId());
            preparedStatement.setTime(2, Time.valueOf(scheduleTimer.getArrival()));
            preparedStatement.setTime(3, Time.valueOf(scheduleTimer.getDeparture()));
            preparedStatement.setInt(4, scheduleTimer.getSequence());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not create schedule timer in database", e);
        }
    }

    // Optional-style effektiv måte: hvis ID finnes i DB. Så update, ellers create
    @Override
    public void saveScheduleTimer(ScheduleTimer scheduleTimer) throws RepositoryException {
        // Hvis objektet har ID, sjekk om den finnes i databasen
        if (scheduleTimer.getScheduleTimerId() != null) {
            Optional<ScheduleTimer> existing = getByScheduleTimerId(scheduleTimer.getScheduleTimerId());

            if (existing.isPresent()) {
                updateScheduleTimer(scheduleTimer);
                return;
            }
        }
        // Hvis ingen ID, eller ID ikke finnes i DB så skal det lages
        createScheduleTimer(scheduleTimer);
    }

    // Oppdaterer en bestemt schedule timer
    @Override
    public void updateScheduleTimer(ScheduleTimer scheduleTimer) throws RepositoryException {
        if (scheduleTimer.getScheduleTimerId() == null) {
            throw new RepositoryException("Cannot update ScheduleTimer without ID");
        }

        String sql = "UPDATE ScheduleTimers SET arrival = ?, departure = ? WHERE schedule_timer_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTime(1, Time.valueOf(scheduleTimer.getArrival()));
            preparedStatement.setTime(2, Time.valueOf(scheduleTimer.getDeparture()));
            preparedStatement.setInt(3, scheduleTimer.getScheduleTimerId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not update schedule timer in database", e);
        }
    }

    // Sletter en timer basert på ID
    @Override
    public void deleteScheduleTimerId(int scheduleTimerId) throws RepositoryException {
        String sql = "DELETE FROM ScheduleTimers WHERE schedule_timer_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scheduleTimerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not delete schedule timer with ID " + scheduleTimerId, e);
        }
    }

    // Hent én timer basert på ID
    @Override
    public Optional<ScheduleTimer> getByScheduleTimerId(int scheduleTimerId) throws RepositoryException {
        String sql = "SELECT schedule_timer_id, arrival, departure FROM ScheduleTimers WHERE schedule_timer_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scheduleTimerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("schedule_timer_id");
                LocalTime arrival = resultSet.getTime("arrival").toLocalTime();
                LocalTime departure = resultSet.getTime("departure").toLocalTime();

                ScheduleTimer timer = new ScheduleTimer(id, arrival, departure);
                return Optional.of(timer);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve schedule timer with ID " + scheduleTimerId, e);
        }
    }

    // Hent alle timer fra tabellen
    @Override
    public ArrayList<ScheduleTimer> getAllScheduleTimer() throws RepositoryException {
        String sql = "SELECT schedule_timer_id, arrival, departure FROM ScheduleTimers";
        ArrayList<ScheduleTimer> timers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("schedule_timer_id");
                LocalTime arrival = resultSet.getTime("arrival").toLocalTime();
                LocalTime departure = resultSet.getTime("departure").toLocalTime();

                ScheduleTimer timer = new ScheduleTimer(id, arrival, departure);
                timers.add(timer);
            }

            return timers;
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve schedule timers from database", e);
        }
    }

    // Hent alle timer for et bestemt schedule_id
    @Override
    public ArrayList<ScheduleTimer> getTimersByScheduleId(int scheduleId) throws RepositoryException {
        String sql = "SELECT schedule_timer_id, arrival, departure FROM ScheduleTimers WHERE schedule_id = ? ORDER BY sequence ASC";
        ArrayList<ScheduleTimer> timers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scheduleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("schedule_timer_id");
                LocalTime arrival = resultSet.getTime("arrival").toLocalTime();
                LocalTime departure = resultSet.getTime("departure").toLocalTime();

                ScheduleTimer timer = new ScheduleTimer(id, arrival, departure);
                timers.add(timer);
            }

            return timers;
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve schedule timers for schedule " + scheduleId, e);
        }
    }
}

