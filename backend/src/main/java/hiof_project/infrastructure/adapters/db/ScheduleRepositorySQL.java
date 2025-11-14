package hiof_project.infrastructure.adapters.db;

import hiof_project.domain.model.transport_system.Stop;
import hiof_project.ports.out.ScheduleRepository;
import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Schedule;
import hiof_project.domain.model.transport_system.ScheduleTimer;


import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class ScheduleRepositorySQL implements ScheduleRepository {
    private Connection connection;
    private final ScheduleTimerRepositorySQL scheduleTimerRepository;

    public ScheduleRepositorySQL(Connection connection) {
        this.connection = connection;
        this.scheduleTimerRepository = new ScheduleTimerRepositorySQL(connection);
    }

    //opprette tidsplan
    public void createSchedule(Schedule schedule) throws RepositoryException {
        String sql = "INSERT INTO Schedules (schedule_id, defined_date) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, schedule.getScheduleId());
            preparedStatement.setDate(2, Date.valueOf(schedule.getDefinedDate()));
            preparedStatement.executeUpdate();

            //Lagre ankomst/avgang liste til tilhørende tidsplan
            for (ScheduleTimer times : schedule.getScheduleTimer()) {
                scheduleTimerRepository.createScheduleTimer(times, schedule.getScheduleId());
            }

        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not create schedule in database", e);
        }
    }

    //lagre tidsplan
    public void updateSchedule(Schedule schedule) throws RepositoryException {
        String sql = "UPDATE Schedules SET defined_date = ? WHERE schedule_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, Date.valueOf(schedule.getDefinedDate()));
            preparedStatement.setInt(2, schedule.getScheduleId());
            preparedStatement.executeUpdate();

            // Oppdater alle ankomst/avgang liste til tilhørende tidsplan
            for (ScheduleTimer times : schedule.getScheduleTimer()) {
                scheduleTimerRepository.updateScheduleTimer(times);
            }

        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not update schedule in database", e);
        }
    }




    public void updateSchedule(Schedule schedule) throws RepositoryException //oppdater tidsplan
    {

    }

    //slett tidsplan basert på IDen
    @Override
    public void deleteScheduleId(int scheduleId) throws RepositoryException {
        String sql = "DELETE FROM Schedules WHERE schedule_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scheduleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not delete schedule with matching ID " + scheduleId, e);
        }
    }

    //hent tidsplan basert på IDen
    public Optional<Schedule> getByScheduleId(int scheduleId) throws RepositoryException {
        String sql = "SELECT * FROM Schedules WHERE schedule_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scheduleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Stop stop = new Stop(
                        resultSet.getInt("stop_id"),
                        resultSet.getString("stop_name"),
                );
                return Optional.of(schedule);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve stop with ID " + stopId, e);
        }


    }

    public ArrayList<Schedule> getAllSchedules() throws RepositoryException //hent alle tidsplaner fra db
    {
        return null;
    }

}
