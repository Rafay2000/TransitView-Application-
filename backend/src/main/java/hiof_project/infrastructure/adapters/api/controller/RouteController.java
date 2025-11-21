package hiof_project.infrastructure.adapters.api.controller;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Route;
import hiof_project.domain.model.transport_system.Stop;
import hiof_project.infrastructure.adapters.api.dto.RouteDTO;
import hiof_project.infrastructure.adapters.api.dto.StopDTO;
import hiof_project.ports.out.RouteRepository;
import hiof_project.ports.out.StopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/routes") // http://localhost:8080/api/routes
public class RouteController {

    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;

    public RouteController(RouteRepository routeRepository,
                           StopRepository stopRepository) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
    }

    //Hente alle ruter fra Azure databasen (1-3)
    @GetMapping
    public ResponseEntity<ArrayList<RouteDTO>> getAllRoutes() throws RepositoryException {
        ArrayList<Route> allRoutes = routeRepository.getAllRoutes();
        ArrayList<RouteDTO> result = new ArrayList<>();

        for (Route route : allRoutes) {
            result.add(mapRouteToDTO(route));
        }

        return ResponseEntity.ok(result);
    }

    //Hente én bestemt rute basert på ID fra Azure databasen (1-3)
    @GetMapping("/{routeId}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable int routeId) throws RepositoryException {
        Optional<Route> optRoute = routeRepository.getByRouteId(routeId);

        if (optRoute.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Route route = optRoute.get();
        RouteDTO dto = mapRouteToDTO(route);

        return ResponseEntity.ok(dto);
    }

    //PRIVATE MAPPER: DOMAIN -> DTO
    private RouteDTO mapRouteToDTO(Route route) throws RepositoryException {
        //Hent alle bussholdeplasser for en rute fra Azure databasen og vis inkludert rute navnet
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
        return new RouteDTO(
                route.getRouteId(),
                route.getRouteName(),
                stopDTOs
        );
    }
}
