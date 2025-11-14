package hiof_project.ports.out;

import hiof_project.domain.model.transport_system.Schedule;
import java.util.ArrayList;
import java.util.Optional;
import hiof_project.domain.exception.RepositoryException;

//CRUD operasjoner for Schedule tabellen i databasen.
public interface ScheduleRepository {
    void createSchedule(Schedule schedule) throws RepositoryException; //opprette tidsplan
    void saveSchedule(Schedule schedule) throws RepositoryException; //lagre tidsplan
    void updateSchedule(Schedule schedule) throws RepositoryException; //oppdater tidsplan
    void deleteScheduleId(int scheduleId) throws RepositoryException; //slett tidsplan basert på IDen
    Optional<Schedule> getByScheduleId(int scheduleId) throws RepositoryException; //hent tidsplan basert på IDen
    ArrayList<Schedule> getAllSchedules() throws RepositoryException; //hent alle tidsplaner fra db
}
