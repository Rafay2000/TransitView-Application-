package hiof_project.infrastructure.adapters.api.controller;

import hiof_project.infrastructure.adapters.api.dto.BusDTO;
import hiof_project.ports.out.BusRepository;
import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Bus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/buses") // http://localhost:8080/api/buses
public class BusController {

    private final BusRepository busRepository;

    public BusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    //Hent alle busser fra Azure databasen
    @GetMapping
    public ResponseEntity<ArrayList<BusDTO>> getAllBuses() throws RepositoryException {
        ArrayList<Bus> allBuses = busRepository.getAllBuses();
        ArrayList<BusDTO> result = new ArrayList<>();

        for (Bus bus : allBuses) {
            result.add(new BusDTO(
                    bus.getVehicleId(),
                    bus.getVehicleName(),
                    bus.getVehicleType(),
                    bus.getCapacity()
            ));
        }

        return ResponseEntity.ok(result);
    }

    //Hent én bestemt buss basert på ID fra Azure databasen (1-5)
    @GetMapping("/{busId}")
    public ResponseEntity<BusDTO> getBusById(@PathVariable int busId) throws RepositoryException {
        Optional<Bus> optBus = busRepository.getByBusId(busId);

        if (optBus.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Bus bus = optBus.get();

        BusDTO newDto = new BusDTO(
                bus.getVehicleId(),
                bus.getVehicleName(),
                bus.getVehicleType(),
                bus.getCapacity()
        );
        return ResponseEntity.ok(newDto);
    }
}
