package java.hiof_project.ports.out;

import java.hiof_project.domain.model.transport_system.Trip;
import java.util.ArrayList;
import java.util.Optional;
import java.hiof_project.domain.exception.RepositoryException;

public interface TripRepository {
    void createTrip(Trip trip) throws RepositoryException;
    void saveTrip(Trip trip) throws RepositoryException;
    void updateTrip(Trip trip) throws RepositoryException;
    void deleteTrip(int id) throws RepositoryException;
    Optional<Trip> getByTripId(int id) throws RepositoryException;
    ArrayList<Trip> getAllTrips() throws RepositoryException;
}
