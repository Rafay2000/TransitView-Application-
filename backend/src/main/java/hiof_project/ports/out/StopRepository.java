package hiof_project.ports.out;

import hiof_project.domain.model.transport_system.Stop;
import java.util.ArrayList;
import java.util.Optional;
import hiof_project.domain.exception.RepositoryException;

//CRUD operasjoner for Stops tabellen i DB
public interface StopRepository {
    void createStop(Stop stop) throws RepositoryException; //opprette holdeplass
    void saveStop(Stop stop) throws RepositoryException; //lagre holdeplass
    void updateStop(Stop stop) throws RepositoryException; //oppdater holdeplass
    void deleteStop(int stopId) throws RepositoryException; //slett holdeplass basert på IDen
    Optional<Stop> getByStopId(int stopId) throws RepositoryException; //hent holdeplass basert på IDen
    ArrayList<Stop> getAllStops() throws RepositoryException; //hent alle holdeplass fra db
}
