package hiof_project.infrastructure.adapters.api.controller;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.RealtimeScheduling;
import hiof_project.infrastructure.adapters.api.dto.RealtimeSchedulingDTO;
import hiof_project.ports.out.RealtimeSchedulingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/realtime") // http://localhost:8080/api/realtime
public class RealtimeSchedulingController {

    private final RealtimeSchedulingRepository realtimeRepository;

    public RealtimeSchedulingController(RealtimeSchedulingRepository realtimeRepository) {
        this.realtimeRepository = realtimeRepository;
    }

    //Hent alle sanntidsoppdateringer fra Azure databasen
    @GetMapping
    public ResponseEntity<ArrayList<RealtimeSchedulingDTO>> getAllRealtimeUpdates() throws RepositoryException {
        ArrayList<RealtimeScheduling> allUpdates = realtimeRepository.getAllUpdatedTime();
        ArrayList<RealtimeSchedulingDTO> result = new ArrayList<>();

        for (RealtimeScheduling updatedTime : allUpdates) {
            result.add(mapToDTO(updatedTime));
        }
        return ResponseEntity.ok(result);
    }

    //Hent én sanntidsoppdatering basert på realtime ID fra Azure databasen (16-30)
    @GetMapping("/{realtimeId}")
    public ResponseEntity<RealtimeSchedulingDTO> getRealtimeById(@PathVariable int realtimeId) throws RepositoryException {
        Optional<RealtimeScheduling> optUpdated = realtimeRepository.getByUpdatedTimeId(realtimeId);

        if (optUpdated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RealtimeScheduling updatedTime = optUpdated.get();
        RealtimeSchedulingDTO dto = mapToDTO(updatedTime);

        return ResponseEntity.ok(dto);
    }

    //Hent sanntidsoppdatering basert på tripId + stopId fra Azure databasen (f.eks: ?tripId=1&stopId=2)
    // http://localhost:8080/api/realtime/search?tripId=1&stopId=2
    @GetMapping("/search")
    public ResponseEntity<RealtimeSchedulingDTO> getRealtimeByTripAndStop(
            @RequestParam int tripId,
            @RequestParam int stopId
    ) throws RepositoryException {

        Optional<RealtimeScheduling> optUpdated =
                realtimeRepository.findUpdatedScheduleByTripAndStop(tripId, stopId);

        if (optUpdated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RealtimeScheduling updatedTime = optUpdated.get();
        RealtimeSchedulingDTO dto = mapToDTO(updatedTime);

        return ResponseEntity.ok(dto);
    }

    // Hent alle sanntider for en bestemt tripId fra Azure databasen (1-3)
    // http://localhost:8080/api/realtime/trip/1
    @GetMapping("/trip/{tripId}")
    public ResponseEntity<ArrayList<RealtimeSchedulingDTO>> getRealtimeByTripId(
            @PathVariable int tripId
    ) throws RepositoryException {

        ArrayList<RealtimeScheduling> updatedTimeList = realtimeRepository.findAllUpdatedScheduleByTrip(tripId);

        ArrayList<RealtimeSchedulingDTO> result = new ArrayList<>();
        for (RealtimeScheduling updatedTime : updatedTimeList) {
            result.add(mapToDTO(updatedTime));
        }
        return ResponseEntity.ok(result);
    }

    //PRIVATE MAPPER: DOMAIN -> DTO
    private RealtimeSchedulingDTO mapToDTO(RealtimeScheduling rs) {
        return new RealtimeSchedulingDTO(
                rs.getRealtimeId(),
                rs.getTripId(),
                rs.getStopId(),
                rs.getUpdatedDeparture(),
                rs.getUpdatedArrival()
        );
    }
}
