package hiof_project.infrastructure.adapters.api.controller;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.service.SearchFilterService;
import hiof_project.ports.out.*;
import hiof_project.domain.model.transport_system.*;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/search")
public class SearchFilterController {

    private final SearchFilterService searchFilterService;
    private final StopRepository stops;
    private final RouteRepository routes;
    private final BusRepository buses;
    private final TripRepository trips;

    public SearchFilterController(SearchFilterService searchFilterService,
                            StopRepository stops,
                            RouteRepository routes,
                            BusRepository buses,
                            TripRepository trips) {
        this.searchFilterService = searchFilterService;
        this.stops = stops;
        this.routes = routes;
        this.buses = buses;
        this.trips = trips;
    }

    //Søk etter bussholdeplasser med en eller flere bokstaver, ellers vis alle
    // http://localhost:8080/api/search/stops?q=
    @GetMapping("/stops")
    public ArrayList<Stop> searchStops(@RequestParam String q)
            throws RepositoryException {

        return searchFilterService.search(
                stops.getAllStops(),
                Stop::getStopName,
                q
        );
    }

    //Søk etter bussholdeplasser med en eller flere bokstaver, ellers vis alle
    // http://localhost:8080/api/search/routes?q=
    @GetMapping("/routes")
    public ArrayList<Route> searchRoutes(@RequestParam String q)
            throws RepositoryException {

        return searchFilterService.search(
                routes.getAllRoutes(),
                Route::getRouteName,
                q
        );
    }

    //Søk etter bussholdeplasser med en eller flere bokstaver, ellers vis alle
    // http://localhost:8080/api/search/buses?q=
    @GetMapping("/buses")
    public ArrayList<Bus> searchBuses(@RequestParam String q)
            throws RepositoryException {

        return searchFilterService.search(
                buses.getAllBuses(),
                Bus::getVehicleName,
                q
        );
    }

    //Søk etter bussholdeplasser med en eller flere bokstaver, ellers vis alle
    // http://localhost:8080/api/search/trips?q=
    @GetMapping("/trips")
    public ArrayList<Trip> searchTrips(@RequestParam String q)
            throws RepositoryException {

        return searchFilterService.search(
                trips.getAllTrips(),
                t -> String.valueOf(t.getTripId()),
                q
        );
    }
}
