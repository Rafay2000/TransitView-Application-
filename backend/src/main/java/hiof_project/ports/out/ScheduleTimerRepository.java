package hiof_project.ports.out;

import hiof_project.domain.model.transport_system.ScheduleTimer;
import java.util.ArrayList;
import java.util.Optional;
import hiof_project.domain.exception.RepositoryException;

//CRUD operasjoner for ScheduleTimer tabellen i databasen.
public interface ScheduleTimerRepository {
    void createScheduleTimer(ScheduleTimer scheduleTimer) throws RepositoryException; //opprette ankomst/avgang
    void saveScheduleTimer(ScheduleTimer scheduleTimer) throws RepositoryException; //lagre ankomst/avgang
    void updateScheduleTimer(ScheduleTimer scheduleTimer) throws RepositoryException; //oppdater ankomst/avgang
    void deleteScheduleTimerId(int scheduleTimerId) throws RepositoryException; //slett ankomst/avgang basert på IDen
    Optional<ScheduleTimer> getByScheduleTimerId(int scheduleId) throws RepositoryException; //hent ankomst/avgang basert på IDen
    ArrayList<ScheduleTimer> getAllScheduleTimer() throws RepositoryException; //hent alle ankomst/avgang fra db
    // Ekstra: hente alle timer for et bestemt schedule_id
    ArrayList<ScheduleTimer> getTimersByScheduleId(int scheduleId) throws RepositoryException;
}
