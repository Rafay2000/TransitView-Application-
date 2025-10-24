package java.hiof_project.ports.out;

import java.hiof_project.domain.model.transport_system.Route;
import java.util.ArrayList;
import java.util.Optional;
import java.hiof_project.domain.exception.RepositoryException;

public interface RouteRepository {
    void createRoute(Route route) throws RepositoryException;
    void saveRoute(Route route) throws RepositoryException;
    void updateRoute(Route route) throws RepositoryException;
    void deleteRoute(int id) throws RepositoryException;
    Optional<Route> getByRouteId(int id) throws RepositoryException;
    ArrayList<Route> getAllRoutes() throws RepositoryException;
}
