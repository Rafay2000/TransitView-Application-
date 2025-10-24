package java.hiof_project.ports.out;

import java.hiof_project.domain.model.transport_system.RealtimeScheduling;
import java.util.ArrayList;
import java.util.Optional;
import java.hiof_project.domain.exception.RepositoryException;

public interface RealtimeSchedulingRepository {
    void createUpdatedTime(RealtimeScheduling update) throws RepositoryException;
    void saveUpdatedTime(RealtimeScheduling update) throws RepositoryException;
    void updateUpdatedTime(RealtimeScheduling update) throws RepositoryException;
    void deleteUpdatedTime(int id) throws RepositoryException;
    Optional<RealtimeScheduling> getByUpdatedTimeId(int id) throws RepositoryException;
    ArrayList<RealtimeScheduling> getAllUpdatedTime() throws RepositoryException;

    Optional<RealtimeScheduling> findByTripAndStop(int tripId, int stopId) throws RepositoryException;
    ArrayList<RealtimeScheduling> findAllByTrip(int tripId) throws RepositoryException;
}
