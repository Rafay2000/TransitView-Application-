package hiof_project.ports.out;

import hiof_project.domain.model.transport_system.Stop;
import java.util.ArrayList;
import java.util.Optional;
import hiof_project.domain.exception.RepositoryException;

//CRUD operasjoner for Stops tabellen i databasen.
public interface StopRepository {
    void createStop(Stop stop, int routeId) throws RepositoryException; //opprette holdeplass til et rute
    void saveStop(Stop stop, int routeId) throws RepositoryException; //lagre holdeplass til et rute
    void updateStop(Stop stop, int routeId) throws RepositoryException; //oppdater holdeplass til et rute
    void deleteStopId(int stopId) throws RepositoryException; //slett holdeplass basert på IDen
    Optional<Stop> getByStopId(int stopId) throws RepositoryException; //hent holdeplass basert på IDen
    ArrayList<Stop> getAllStops() throws RepositoryException; //hent alle holdeplass fra db
    ArrayList<Stop> getStopsByRouteId(int routeId) throws RepositoryException; //hent alle holdeplass fra et bestemt rute i db
}
