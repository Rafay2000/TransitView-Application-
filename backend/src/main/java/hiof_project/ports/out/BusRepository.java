package java.hiof_project.ports.out;

import java.hiof_project.domain.model.transport_system.Bus;
import java.util.ArrayList;
import java.util.Optional;
import java.hiof_project.domain.exception.RepositoryException;

public interface BusRepository {
    void createBus(Bus bus) throws RepositoryException;
    void saveBus(Bus bus) throws RepositoryException;
    void updateBus(Bus bus) throws RepositoryException;
    void deleteBusId(int id) throws RepositoryException;
    Optional<Bus> getByBusId(int id) throws RepositoryException;
    ArrayList<Bus> getAllBuses() throws RepositoryException;
}
