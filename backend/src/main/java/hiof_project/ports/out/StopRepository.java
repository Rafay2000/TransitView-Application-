package java.hiof_project.ports.out;

import java.hiof_project.domain.model.transport_system.Stop;
import java.util.ArrayList;
import java.util.Optional;
import java.hiof_project.domain.exception.RepositoryException;

public interface StopRepository {
    void createStop(Stop stop) throws RepositoryException;
    void saveStop(Stop stop) throws RepositoryException;
    void updateStop(Stop stop) throws RepositoryException;
    void deleteStop(int id) throws RepositoryException;
    Optional<Stop> getByStopId(int id) throws RepositoryException;
    ArrayList<Stop> getAllStops() throws RepositoryException;
}
