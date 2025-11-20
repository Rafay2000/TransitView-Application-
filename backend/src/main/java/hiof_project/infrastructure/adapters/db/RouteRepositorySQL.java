package hiof_project.infrastructure.adapters.db;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Route;
import hiof_project.domain.model.transport_system.Stop;
import hiof_project.ports.out.RouteRepository;
import hiof_project.ports.out.StopRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RouteRepositorySQL implements RouteRepository {
    private Connection connection;

    public RouteRepositorySQL(Connection connection) {
        this.connection = connection;
    }

    //Oppretter rute verdier basert på datatyper fra Routes klassen og legger det i SQL databasen
    @Override
    public void createRoute(Route route) throws RepositoryException {
        String sql = "INSERT INTO Routes (route_id, route_name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, route.getRouteId());
            preparedStatement.setString(2, route.getRouteName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not create route in database", e);
        }
    }

    // Optional-style effektiv måte: hvis ID finnes i DB. Så update, ellers create
    @Override
    public void saveRoute(Route route) throws RepositoryException {
        Optional<Route> existing = getByRouteId(route.getRouteId());

        if (existing.isPresent()) {
            updateRoute(route);
        } else {
            createRoute(route);
        }
    }

    //Oppdaterer buss verdier på et bestemt buss id fra SQL tabellen
    @Override
    public void updateRoute(Route route) throws RepositoryException {
        String sql = "UPDATE Routes SET route_name = ? WHERE route_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, route.getRouteName());
            preparedStatement.setInt(2, route.getRouteId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not update route in database", e);
        }
    }

    //Sletter buss verdier basert på datatyper fra Bus klassen og legger det i SQL databasen
    @Override
    public void deleteRouteId(int routeId) throws RepositoryException {
        String sql = "DELETE FROM Routes WHERE route_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, routeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not delete route with matching ID " + routeId, e);
        }
    }

    //Henter et bestemt buss id fra bus tabellen i SQL databasen
    @Override
    public Optional<Route> getByRouteId(int routeId) throws RepositoryException {
        String sql = "SELECT route_id, route_name FROM Routes WHERE route_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, routeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Bruk StopRepositorySQL direkte til å hente alle bussholdeplasser til et rute
                StopRepository stopRepo = new StopRepositorySQL(connection);
                ArrayList<Stop> stopsOnRoute = stopRepo.getStopsByRouteId(routeId);

                Route route = new Route(
                        resultSet.getInt("route_id"),
                        resultSet.getString("route_name"),
                        stopsOnRoute
                );
                return Optional.of(route);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RepositoryException("Could not retrieve route with id " + routeId, e);
        }
    }

    //Henter alle ruter fra Routes tabellen i SQL databasen
    @Override
    public ArrayList<Route> getAllRoutes() throws RepositoryException {
        String sql = "SELECT route_id, route_name FROM Routes";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ArrayList<Route> routeList = new ArrayList<>();
            StopRepository stopsRepo = new StopRepositorySQL(connection); //inkludert repo for stops

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int routeId = resultSet.getInt("route_id");
                String routeName = resultSet.getString("route_name");

                ArrayList<Stop> stops = stopsRepo.getStopsByRouteId(routeId); //Hent alle bussholdeplasser for en rute

                Route route = new Route(routeId, routeName, stops); //Ruter med alle bussholdeplasser
                routeList.add(route);
            }

            return routeList;

        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve buses from the database", e);
        }
    }
}

