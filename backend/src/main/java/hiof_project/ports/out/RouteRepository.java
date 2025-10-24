package hiof_project.ports.out;

import hiof_project.domain.model.transport_system.Route;
import java.util.ArrayList;
import java.util.Optional;
import hiof_project.domain.exception.RepositoryException;

//CRUD operasjoner for Route tabellen i databasen.
public interface RouteRepository {
    void createRoute(Route route) throws RepositoryException; //opprette rute
    void saveRoute(Route route) throws RepositoryException; //lagre rute
    void updateRoute(Route route) throws RepositoryException; //oppdater rute
    void deleteRoute(int routeId) throws RepositoryException; //slett rute basert på IDen
    Optional<Route> getByRouteId(int routeId) throws RepositoryException; //hent rute basert på IDen
    ArrayList<Route> getAllRoutes() throws RepositoryException; //hent alle ruter fra db
}
