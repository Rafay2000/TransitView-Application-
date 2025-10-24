package hiof_project.ports.out;

import hiof_project.domain.model.transport_system.RealtimeScheduling;
import java.util.ArrayList;
import java.util.Optional;
import hiof_project.domain.exception.RepositoryException;

//CRUD operasjoner for Bus tabellen i databasen.
public interface RealtimeSchedulingRepository {
    void createUpdatedTime(RealtimeScheduling update) throws RepositoryException; //opprette oppdatert sanntider
    void saveUpdatedTime(RealtimeScheduling update) throws RepositoryException; //lagre oppdatert sanntider
    void updateUpdatedTime(RealtimeScheduling update) throws RepositoryException; //oppdater sanntider
    void deleteUpdatedTime(int realtimeId) throws RepositoryException; //slett oppdatert sanntid basert på IDen
    Optional<RealtimeScheduling> getByUpdatedTimeId(int realtimeId) throws RepositoryException; //hent oppdatert sanntid IDen
    ArrayList<RealtimeScheduling> getAllUpdatedTime() throws RepositoryException; //hent alle oppdaterte sanntid fra db

    //finn sanntid fra tripId og stopId i databasen
    Optional<RealtimeScheduling> findByTripAndStop(int tripId, int stopId) throws RepositoryException;
    //finn alle sanntider fra tripId i databasen
    ArrayList<RealtimeScheduling> findAllByTrip(int tripId) throws RepositoryException;
}
