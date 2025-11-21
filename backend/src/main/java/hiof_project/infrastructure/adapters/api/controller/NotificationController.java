package hiof_project.infrastructure.adapters.api.controller;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.RealtimeScheduling;
import hiof_project.domain.service.NotificationService;
import hiof_project.ports.out.RealtimeSchedulingRepository;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/notifications") //http://localhost:8080/api/notifications (må refresh fra POST før det kan vises)
public class NotificationController {

    private final RealtimeSchedulingRepository realtimeRepo;
    private final NotificationService notifService;

    public NotificationController(RealtimeSchedulingRepository realtimeRepo,
                                  NotificationService notifService) {
        this.realtimeRepo = realtimeRepo;
        this.notifService = notifService;
    }

    //Må refresh først før push-varsel kan vises
    // POST: http://localhost:8080/api/notifications/refresh
    @PostMapping("/refresh")
    public ArrayList<String> refreshNotifications() {
        try {
            notifService.clearNotifications();

            ArrayList<RealtimeScheduling> all = realtimeRepo.getAllUpdatedTime();

            for (RealtimeScheduling rs : all) {
                notifService.createNotification(rs);
            }

            return notifService.getAllNotifications();

        } catch (RepositoryException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // GET: http://localhost:8080/api/notifications
    @GetMapping
    public ArrayList<String> getAllNotifications() {
        return notifService.getAllNotifications();
    }
}

