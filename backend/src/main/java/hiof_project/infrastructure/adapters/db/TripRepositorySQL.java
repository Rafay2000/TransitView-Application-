package hiof_project.infrastructure.adapters.db;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Trip;
import hiof_project.domain.model.transport_system.Route;
import hiof_project.domain.model.transport_system.Schedule;
import hiof_project.domain.model.transport_system.Bus;
import hiof_project.ports.out.TripRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class TripRepositorySQL implements TripRepository {

    private Connection connection;

    public TripRepositorySQL(Connection connection) {
        this.connection = connection;
    }

    //Oppretter tur verdier basert på datatyper fra Trips tabellen med route_id, schedule_id og bus_id som FOREIGN KEY
    @Override
    public void createTrip(Trip trip) throws RepositoryException {
        String sql = "INSERT INTO Trips (trip_id, route_id, schedule_id, bus_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, trip.getTripId());
            preparedStatement.setInt(2, trip.getRoute().getRouteId());
            preparedStatement.setInt(3, trip.getSchedule().getScheduleId());
            preparedStatement.setInt(4, trip.getBus().getVehicleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not create trip in database", e);
        }
    }

    // Optional-style effektiv måte: hvis ID finnes i DB. Så update, ellers create
    @Override
    public void saveTrip(Trip trip) throws RepositoryException {
        Optional<Trip> existing = getByTripId(trip.getTripId());

        if (existing.isPresent()) {
            updateTrip(trip);
        } else {
            createTrip(trip);
        }
    }

    //Oppdater tur verdier på en bestemt tur fra Trips tabellen med route_id, schedule_id og bus_id som FOREIGN KEY
    @Override
    public void updateTrip(Trip trip) throws RepositoryException {
        String sql = "UPDATE Trips SET route_id = ?, schedule_id = ?, bus_id = ? WHERE trip_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, trip.getRoute().getRouteId());
            preparedStatement.setInt(2, trip.getSchedule().getScheduleId());
            preparedStatement.setInt(3, trip.getBus().getVehicleId());
            preparedStatement.setInt(4, trip.getTripId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not update trip in database", e);
        }
    }

    //Sletter tur verdier basert på datatyper fra Trips tabelleen
    @Override
    public void deleteTripId(int tripId) throws RepositoryException {
        String sql = "DELETE FROM Trips WHERE trip_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, tripId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not delete stop with ID " + tripId, e);
        }
    }

    //Henter et bestemt tur id fra Trips tabellen i SQL databasen
    @Override
    public Optional<Trip> getByTripId(int tripId) throws RepositoryException {
        String sql = "SELECT * FROM Trips WHERE trip_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, tripId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int routeId = resultSet.getInt("route_id");
                int scheduleId = resultSet.getInt("schedule_id");
                int vehicleId = resultSet.getInt("bus_id");

                Route route = new RouteRepositorySQL(connection).getByRouteId(routeId)
                        .orElseThrow(() -> new RepositoryException("Route not found for ID " + routeId));
                Schedule schedule = new ScheduleRepositorySQL(connection).getByScheduleId(scheduleId)
                        .orElseThrow(() -> new RepositoryException("Schedule not found for ID " + scheduleId));
                Bus bus = new BusRepositorySQL(connection).getByBusId(vehicleId)
                        .orElseThrow(() -> new RepositoryException("Bus not found for ID " + vehicleId));

                Trip trip = new Trip(tripId, route, schedule, bus);
                return Optional.of(trip);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve trip with ID " + tripId, e);
        }
    }

    //Henter alle tur id fra Trips tabellen i SQL databasen
    public ArrayList<Trip> getAllTrips() throws RepositoryException {
        String sql = "SELECT * FROM Trips";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Trip> tripList = new ArrayList<>();

            while (resultSet.next()) {
                int tripId = resultSet.getInt("trip_id");
                int routeId = resultSet.getInt("route_id");
                int scheduleId = resultSet.getInt("schedule_id");
                int vehicleId = resultSet.getInt("bus_id");

                Route route = new RouteRepositorySQL(connection).getByRouteId(routeId)
                        .orElseThrow(() -> new RepositoryException("Route not found for ID " + routeId));
                Schedule schedule = new ScheduleRepositorySQL(connection).getByScheduleId(scheduleId)
                        .orElseThrow(() -> new RepositoryException("Schedule not found for ID " + scheduleId));
                Bus bus = new BusRepositorySQL(connection).getByBusId(vehicleId)
                        .orElseThrow(() -> new RepositoryException("Bus not found for ID " + vehicleId));

                Trip trip = new Trip(tripId, route, schedule, bus);
                tripList.add(trip);
            }

            return tripList;
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve trips from database", e);
        }
    }
}

