package hiof_project.domain.service;

import hiof_project.domain.model.transport_system.RealtimeScheduling;
import hiof_project.domain.model.transport_system.Stop;
import hiof_project.domain.exception.RepositoryException;
import hiof_project.ports.out.StopRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class NotificationService {

    private final StopRepository stopsRepo;
    private final ArrayList<String> notifications = new ArrayList<>();

    public NotificationService(StopRepository stopsRepo) {
        this.stopsRepo = stopsRepo;
    }

    public ArrayList<String> getAllNotifications() {
        return notifications;
    }

    public void clearNotifications() {
        notifications.clear();
    }

    public String createNotification(RealtimeScheduling rs) {

        String status = rs.getStatus();
        if (status == null) status = "";

        String lower = status.toLowerCase();
        String type;

        if (lower.contains("kansell")) {
            type = "CANCELLED";
        } else if (lower.contains("forsink")) {
            type = "DELAYED";
        } else if (lower.contains("ingen forsinkelser")) {
            type = "ON_TIME";
        } else if (lower.contains("ingen sanntid")) {
            type = "NO_DATA";
        } else {
            type = "INFO";
        }

        // ---- Henter busshodleplass navnet fra StopRepository ----
        String stopName = "Stop " + rs.getStopId(); // fallback hvis ikke finnes
        try {
            Optional<Stop> optStop = stopsRepo.getByStopId(rs.getStopId());
            if (optStop.isPresent()) {
                stopName = optStop.get().getStopName() + " (ID " + rs.getStopId() + ")";
            }
        } catch (RepositoryException e) {
            // hvis lookup feiler, bruk fallback
        }

        String message;
        switch (type) {
            case "CANCELLED":
                message = "Trip " + rs.getTripId() +
                        " er KANSELLERT på " + stopName +
                        " (" + status + ")";
                break;

            case "DELAYED":
                message = "Trip " + rs.getTripId() +
                        " er FORSINKET på " + stopName +
                        " (" + status + ")";
                break;

            case "ON_TIME":
                message = "Trip " + rs.getTripId() +
                        " er I RUTE ved " + stopName + "turen fortsetter";
                break;

            case "NO_DATA":
                message = "Trip " + rs.getTripId() +
                        " har INGEN OPPDATERINGER ved " + stopName;
                break;

            default:
                message = "Trip " + rs.getTripId() +
                        " er I RUTE ved " + stopName +
                        " — turen fortsetter: " + status;
                break;
        }

        notifications.add(message);
        return message;
    }
}