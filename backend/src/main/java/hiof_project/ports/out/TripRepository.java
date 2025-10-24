package hiof_project.ports.out;

import hiof_project.domain.model.transport_system.Trip;
import java.util.ArrayList;
import java.util.Optional;
import hiof_project.domain.exception.RepositoryException;

//CRUD operasjoner for Trip tabellen i databasen
public interface TripRepository {
    void createTrip(Trip trip) throws RepositoryException; //opprette tur
    void saveTrip(Trip trip) throws RepositoryException; //lagre tur
    void updateTrip(Trip trip) throws RepositoryException; //oppdater tur
    void deleteTrip(int tripId) throws RepositoryException; //slett tur basert på IDen
    Optional<Trip> getByTripId(int tripId) throws RepositoryException; //hent tur basert på IDen
    ArrayList<Trip> getAllTrips() throws RepositoryException; //hent alle turer fra db
}
