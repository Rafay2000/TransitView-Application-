package hiof_project.infrastructure.adapters.db;
/*
import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.RealtimeScheduling;
import hiof_project.ports.out.RealtimeSchedulingRepository;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class RealtimeSchedulingRepositorySQL implements RealtimeSchedulingRepository {

    private Connection connection;

    public RealtimeSchedulingRepositorySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUpdatedTime(RealtimeScheduling update) throws RepositoryException {
        String sql = "INSERT INTO RealtimeScheduling (trip_id, stop_id, updated_arrival, updated_departure, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, update.getTripId());
            preparedStatement.setInt(2, update.getStopId());
            preparedStatement.setTime(3, Time.valueOf(update.getUpdatedArrival()));
            preparedStatement.setTime(4, Time.valueOf(update.getUpdatedDeparture()));
            preparedStatement.setString(5, update.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not create realtime schedule in database", e);
        }
    }

    // Optional-style effektiv måte: hvis ID finnes i DB. Så update, ellers create
    @Override
    public void saveUpdatedTime(RealtimeScheduling update) throws RepositoryException {
        if (update.getRealtimeId() > 0) {
            Optional<RealtimeScheduling> existing = getByUpdatedTimeId(update.getRealtimeId());
            if (existing.isPresent()) {
                updateUpdatedTime(update);
                return;
            }
        }
        createUpdatedTime(update);
    }

    @Override
    public void updateUpdatedTime(RealtimeScheduling update) throws RepositoryException {
        String sql = "UPDATE RealtimeScheduling SET trip_id = ?, stop_id = ?, " +
                "updated_arrival = ?, updated_departure = ?, status = ? WHERE realtime_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, update.getTripId());
            preparedStatement.setInt(2, update.getStopId());
            preparedStatement.setTime(3, Time.valueOf(update.getUpdatedArrival()));
            preparedStatement.setTime(4, Time.valueOf(update.getUpdatedDeparture()));
            preparedStatement.setString(5, update.getStatus());
            preparedStatement.setInt(6, update.getRealtimeId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not update realtime schedule in database", e);
        }
    }

    @Override
    public void deleteUpdatedTimeId(int realtimeId) throws RepositoryException {
        String sql = "DELETE FROM RealtimeScheduling WHERE realtime_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, realtimeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not delete realtime schedule with ID " + realtimeId, e);
        }
    }

    @Override
    public Optional<RealtimeScheduling> getByUpdatedTimeId(int realtimeId) throws RepositoryException {
        String sql = "SELECT realtime_id, trip_id, stop_id, updated_arrival, updated_departure, status " +
                "FROM RealtimeScheduling WHERE realtime_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, realtimeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("realtime_id");
                int tripId = resultSet.getInt("trip_id");
                int stopId = resultSet.getInt("stop_id");

                Time arrTime = resultSet.getTime("updated_arrival");
                Time depTime = resultSet.getTime("updated_departure");

                LocalTime updatedArrival = arrTime != null ? arrTime.toLocalTime() : null;
                LocalTime updatedDeparture = depTime != null ? depTime.toLocalTime() : null;

                String status = resultSet.getString("status");

                RealtimeScheduling realtime = new RealtimeScheduling(
                        id,
                        tripId,
                        stopId,
                        updatedArrival,
                        updatedDeparture,
                        status
                );
                return Optional.of(realtime);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve realtime schedule with ID " + realtimeId, e);
        }
    }

    @Override
    public ArrayList<RealtimeScheduling> getAllUpdatedTime() throws RepositoryException {
        String sql = "SELECT realtime_id, trip_id, stop_id, updated_arrival, updated_departure, status " +
                "FROM RealtimeScheduling";
        ArrayList<RealtimeScheduling> result = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("realtime_id");
                int tripId = resultSet.getInt("trip_id");
                int stopId = resultSet.getInt("stop_id");

                Time arrTime = resultSet.getTime("updated_arrival");
                Time depTime = resultSet.getTime("updated_departure");

                LocalTime updatedArrival = arrTime != null ? arrTime.toLocalTime() : null;
                LocalTime updatedDeparture = depTime != null ? depTime.toLocalTime() : null;

                String status = resultSet.getString("status");

                RealtimeScheduling realtime = new RealtimeScheduling(
                        id,
                        tripId,
                        stopId,
                        updatedArrival,
                        updatedDeparture,
                        status
                );
                result.add(realtime);
            }

            return result;
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve realtime schedules from database", e);
        }
    }

    @Override
    public Optional<RealtimeScheduling> findUpdatedScheduleByTripAndStop(int tripId, int stopId) throws RepositoryException {
        String sql = "SELECT TOP 1 realtime_id, trip_id, stop_id, updated_arrival, updated_departure, status " +
                "FROM RealtimeScheduling WHERE trip_id = ? AND stop_id = ? ORDER BY realtime_id DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, tripId);
            preparedStatement.setInt(2, stopId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("realtime_id");
                Time arrTime = resultSet.getTime("updated_arrival");
                Time depTime = resultSet.getTime("updated_departure");

                LocalTime updatedArrival = arrTime != null ? arrTime.toLocalTime() : null;
                LocalTime updatedDeparture = depTime != null ? depTime.toLocalTime() : null;

                String status = resultSet.getString("status");

                RealtimeScheduling realtime = new RealtimeScheduling(
                        id,
                        tripId,
                        stopId,
                        updatedArrival,
                        updatedDeparture,
                        status
                );
                return Optional.of(realtime);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve realtime schedule for trip " +
                    tripId + " and stop " + stopId, e);
        }
    }

    @Override
    public ArrayList<RealtimeScheduling> findAllUpdatedScheduleByTrip(int tripId) throws RepositoryException {
        String sql = "SELECT realtime_id, trip_id, stop_id, updated_arrival, updated_departure, status " +
                "FROM RealtimeScheduling WHERE trip_id = ? ORDER BY realtime_id DESC";

        ArrayList<RealtimeScheduling> result = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, tripId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("realtime_id");
                int stopId = resultSet.getInt("stop_id");

                Time arrTime = resultSet.getTime("updated_arrival");
                Time depTime = resultSet.getTime("updated_departure");

                LocalTime updatedArrival = arrTime != null ? arrTime.toLocalTime() : null;
                LocalTime updatedDeparture = depTime != null ? depTime.toLocalTime() : null;

                String status = resultSet.getString("status");

                RealtimeScheduling realtime = new RealtimeScheduling(
                        id,
                        tripId,
                        stopId,
                        updatedArrival,
                        updatedDeparture,
                        status
                );
                result.add(realtime);
            }

            return result;
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve realtime schedules for trip " + tripId, e);
        }
    }
}

*/