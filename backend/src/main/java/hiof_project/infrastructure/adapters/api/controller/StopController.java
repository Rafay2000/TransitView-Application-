package hiof_project.infrastructure.adapters.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

import hiof_project.domain.model.transport_system.Stop;
import hiof_project.infrastructure.adapters.api.dto.StopDTO;
import hiof_project.ports.out.StopRepository;
import hiof_project.domain.exception.RepositoryException;

@RestController
@RequestMapping("/api/stops") //http://localhost:8080/api/stops
public class StopController {

    private final StopRepository stopRepository;

    public StopController(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    //Hente alle bussholdeplasser fra Azure databasen
    @GetMapping
    public ResponseEntity<ArrayList<StopDTO>> getAllStops() throws RepositoryException {
        ArrayList<Stop> allStops = stopRepository.getAllStops();
        ArrayList<StopDTO> result = new ArrayList<>();

        for (Stop stop : allStops) {
            result.add(new StopDTO(
                    stop.getStopId(),
                    stop.getStopName(),
                    stop.getDistanceInKm(),
                    stop.getTimeToNextStop(),
                    stop.getDescriptionNextStop()
            ));
        }
        return ResponseEntity.ok(result);
    }

    //Hent et bestemt bussholdeplass ID fra Azure databasen (1-5 / 11-15 / 21-25)
    @GetMapping("/{stopId}")
    public ResponseEntity<StopDTO> getStopById(@PathVariable int stopId) throws RepositoryException {
        Optional<Stop> optStop = stopRepository.getByStopId(stopId);

        if (optStop.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Stop stop = optStop.get();

        StopDTO newDto = new StopDTO(
                stop.getStopId(),
                stop.getStopName(),
                stop.getDistanceInKm(),
                stop.getTimeToNextStop(),
                stop.getDescriptionNextStop()
        );

        return ResponseEntity.ok(newDto);
    }
}
