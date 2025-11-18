package hiof_project.infrastructure.adapters.db;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Stop;
import hiof_project.ports.out.StopRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class StopRepositorySQL implements StopRepository {

    private Connection connection;

    public StopRepositorySQL(Connection connection) {
        this.connection = connection;
    }

    //Oppretter bussholdeplass verdier basert på datatyper fra Stops tabellen med route_id som FOREIGN KEY
    @Override
    public void createStop(Stop stop, int routeId) throws RepositoryException {
        String sql = "INSERT INTO Stops (stop_id, stop_name, distance_to_next_km, " +
                "time_to_next_stop, desc_next_stop, route_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, stop.getStopId());
            preparedStatement.setString(2, stop.getStopName());
            preparedStatement.setDouble(3, stop.getDistanceInKm());
            preparedStatement.setInt(4, stop.getTimeToNextStop());
            preparedStatement.setString(5, stop.getDescriptionNextStop());
            preparedStatement.setInt(6, routeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not create stop in database", e);
        }
    }

    // Optional-style effektiv måte: hvis ID finnes i DB. Så update, ellers create
    @Override
    public void saveStop(Stop stop, int routeId) throws RepositoryException {
        Optional<Stop> existing = getByStopId(stop.getStopId());

        if (existing.isPresent()) {
            updateStop(stop, routeId);
        } else {
            createStop(stop, routeId);
        }
    }

    //Oppdater bussholdeplass verdier på en bestemt bussholdeplass fra Stops tabellen med route_id som FOREIGN KEY
    @Override
    public void updateStop(Stop stop, int routeId) throws RepositoryException {
        String sql = "UPDATE Stops SET stop_name = ?, distance_to_next_km = ?, time_to_next_stop = ?, " +
                "desc_next_stop = ?, route_id = ? WHERE stop_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, stop.getStopName());
            preparedStatement.setDouble(2, stop.getDistanceInKm());
            preparedStatement.setInt(3, stop.getTimeToNextStop());
            preparedStatement.setString(4, stop.getDescriptionNextStop());
            preparedStatement.setInt(5, routeId);
            preparedStatement.setInt(6, stop.getStopId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not update stop in database", e);
        }
    }

    //Sletter bussholdeplass verdier basert på datatyper fra Stops tabelleen
    @Override
    public void deleteStopId(int stopId) throws RepositoryException {
        String sql = "DELETE FROM Stops WHERE stop_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, stopId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not delete stop with ID " + stopId, e);
        }
    }

    //Henter et bestemt stop id fra Stops tabellen i SQL databasen
    @Override
    public Optional<Stop> getByStopId(int stopId) throws RepositoryException {
        String sql = "SELECT * FROM Stops WHERE stop_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, stopId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Stop stop = new Stop(
                        resultSet.getInt("stop_id"),
                        resultSet.getString("stop_name"),
                        resultSet.getDouble("distance_to_next_km"),
                        resultSet.getInt("time_to_next_stop"),
                        resultSet.getString("desc_next_stop")
                );
                return Optional.of(stop);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve stop with ID " + stopId, e);
        }
    }

    //Henter alle bussholdeplasser id fra Stops tabellen i SQL databasen
    @Override
    public ArrayList<Stop> getAllStops() throws RepositoryException {
        String sql = "SELECT * FROM Stops";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ArrayList<Stop> stopList = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int stopId = resultSet.getInt("stop_id");
                String stopName = resultSet.getString("stop_name");
                double distanceInKm = resultSet.getDouble("distance_to_next_km");
                int timeToNextStop = resultSet.getInt("time_to_next_stop");
                String descriptionNextStop = resultSet.getString("desc_next_stop");

                Stop stop = new Stop(stopId, stopName, distanceInKm , timeToNextStop, descriptionNextStop);
                stopList.add(stop);
            }

            return stopList;
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve stops from database", e);
        }
    }


    //Henter alle bussholdeplasser id fra et bestemt rute ra Route tabellen i SQL databasen
    @Override
    public ArrayList<Stop> getStopsByRouteId(int routeId) throws RepositoryException {
        String sql = "SELECT stop_id, stop_name, distance_to_next_km, time_to_next_stop, " +
                "desc_next_stop FROM Stops WHERE route_id = ?";
        ArrayList<Stop> stops = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, routeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Stop stop = new Stop(
                        resultSet.getInt("stop_id"),
                        resultSet.getString("stop_name"),
                        resultSet.getDouble("distance_to_next_km"),
                        resultSet.getInt("time_to_next_stop"),
                        resultSet.getString("desc_next_stop")
                );
                stops.add(stop);
            }

            return stops;
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve stops from route " + routeId, e);
        }
    }
}

