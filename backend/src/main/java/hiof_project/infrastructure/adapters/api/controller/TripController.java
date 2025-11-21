package hiof_project.infrastructure.adapters.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

// ===== DOMAIN =====
import hiof_project.domain.model.transport_system.Bus;
import hiof_project.domain.model.transport_system.Route;
import hiof_project.domain.model.transport_system.Schedule;
import hiof_project.domain.model.transport_system.ScheduleTimer;
import hiof_project.domain.model.transport_system.Stop;
import hiof_project.domain.model.transport_system.Trip;

// ===== DTO =====
import hiof_project.infrastructure.adapters.api.dto.BusDTO;
import hiof_project.infrastructure.adapters.api.dto.RouteDTO;
import hiof_project.infrastructure.adapters.api.dto.ScheduleDTO;
import hiof_project.infrastructure.adapters.api.dto.ScheduleTimerDTO;
import hiof_project.infrastructure.adapters.api.dto.StopDTO;
import hiof_project.infrastructure.adapters.api.dto.TripDTO;

// ===== REPOSITORY =====
import hiof_project.ports.out.TripRepository;
import hiof_project.ports.out.StopRepository;
import hiof_project.ports.out.ScheduleTimerRepository;
import hiof_project.domain.exception.RepositoryException;

@RestController
@RequestMapping("/api/trips") //http://localhost:8080/api/trips
public class TripController {

    private final TripRepository tripRepository;
    private final StopRepository stopRepository;
    private final ScheduleTimerRepository scheduleTimerRepository;

    public TripController(TripRepository tripRepository,
                          StopRepository stopRepository,
                          ScheduleTimerRepository scheduleTimerRepository) {
        this.tripRepository = tripRepository;
        this.stopRepository = stopRepository;
        this.scheduleTimerRepository = scheduleTimerRepository;
    }

    //Hent et bestemt tur ID fra Azure databasen (1-3)
    @GetMapping("/{tripId}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable int tripId) throws RepositoryException {
        Optional<Trip> optTrip = tripRepository.getByTripId(tripId);

        if (optTrip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Trip trip = optTrip.get();
        TripDTO newDto = mapTripToDTO(trip);

        return ResponseEntity.ok(newDto);
    }

    //Hente alle turer fra Azure databasen
    @GetMapping
    public ResponseEntity<ArrayList<TripDTO>> getAllTrips() throws RepositoryException {
        ArrayList<Trip> allTrips = tripRepository.getAllTrips();
        ArrayList<TripDTO> result = new ArrayList<>();

        for (Trip trip : allTrips) {
            result.add(mapTripToDTO(trip));
        }

        return ResponseEntity.ok(result);
    }

    //PRIVATE MAPPER: DOMAIN -> DTO. Skal hente stops, route, scheduletimer og schedule for å vise full oversikt

    private TripDTO mapTripToDTO(Trip trip) throws RepositoryException {
        Route route = trip.getRoute();
        Schedule schedule = trip.getSchedule();
        Bus bus = trip.getBus();

        // ---- Hent stops fra StopRepository basert på routeId ----
        ArrayList<Stop> allStops = stopRepository.getStopsByRouteId(route.getRouteId());
        ArrayList<StopDTO> stopDTOs = new ArrayList<>();

        for (Stop stop : allStops) {
            stopDTOs.add(new StopDTO(
                    stop.getStopId(),
                    stop.getStopName(),
                    stop.getDistanceInKm(),
                    stop.getTimeToNextStop(),
                    stop.getDescriptionNextStop()
            ));
        }

        RouteDTO routeDTO = new RouteDTO(
                route.getRouteId(),
                route.getRouteName(),
                stopDTOs
        );

        // ---- Hent schedule timers fra ScheduleTimerRepository basert på scheduleId ----
        ArrayList<ScheduleTimer> allTimers = scheduleTimerRepository.getTimersByScheduleId(schedule.getScheduleId());
        ArrayList<ScheduleTimerDTO> timerDTOs = new ArrayList<>();

        for (ScheduleTimer hourMinutes : allTimers) {
            timerDTOs.add(new ScheduleTimerDTO(
                    hourMinutes.getScheduleTimerId(),
                    hourMinutes.getArrival(),
                    hourMinutes.getDeparture()
            ));
        }

        ScheduleDTO scheduleDTO = new ScheduleDTO(
                schedule.getScheduleId(),
                schedule.getDefinedDate(),
                timerDTOs
        );

        // ---- Bus -> BusDTO ----
        BusDTO busDTO = new BusDTO(
                bus.getVehicleId(),
                bus.getVehicleName(),
                bus.getVehicleType(),
                bus.getCapacity()
        );

        // ---- TripDTO ----
        return new TripDTO(
                trip.getTripId(),
                routeDTO,
                scheduleDTO,
                busDTO
        );
    }
}
