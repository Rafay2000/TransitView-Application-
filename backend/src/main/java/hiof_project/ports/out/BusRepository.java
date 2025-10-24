package hiof_project.ports.out;

import hiof_project.domain.model.transport_system.Bus;
import java.util.ArrayList;
import java.util.Optional;
import hiof_project.domain.exception.RepositoryException;

//CRUD operasjoner for Bus tabellen i databasen
public interface BusRepository {
    void createBus(Bus bus) throws RepositoryException; //opprette buss
    void saveBus(Bus bus) throws RepositoryException; //lagre buss
    void updateBus(Bus bus) throws RepositoryException; //oppdater buss
    void deleteBusId(int vehicleId) throws RepositoryException; //slett buss basert på IDen
    Optional<Bus> getByBusId(int vehicleId) throws RepositoryException; //hent buss basert på spesifikk ID fra db
    ArrayList<Bus> getAllBuses() throws RepositoryException; //hent alle bussene fra databasen
}
