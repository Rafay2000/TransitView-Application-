package hiof_project.infrastructure.adapters.api.controller;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.service.FavoriteService;
import hiof_project.ports.out.*;
import hiof_project.domain.model.transport_system.*;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites") //http://localhost:8080/api/favorites
public class FavoriteController {

    private final FavoriteService favs;
    private final StopRepository stopsRepo;
    private final RouteRepository routesRepo;
    private final BusRepository busesRepo;
    private final TripRepository tripsRepo;

    public FavoriteController(FavoriteService favs,
                              StopRepository stopsRepo,
                              RouteRepository routesRepo,
                              BusRepository busesRepo,
                              TripRepository tripsRepo) {
        this.favs = favs;
        this.stopsRepo = stopsRepo;
        this.routesRepo = routesRepo;
        this.busesRepo = busesRepo;
        this.tripsRepo = tripsRepo;
    }

    // -------- Bussholdeplasser add/remove/getAll --------
    // POST: http://localhost:8080/api/favorites/stop/{id}
    @PostMapping("/stop/{id}")
    public String addStop(@PathVariable int id) {
        boolean addedStop = favs.addStopToFavorites(id);

        if (addedStop) {
            return "Stop " + id + " ble lagt til i favoritter.";
        }
        return "Stop " + id + " er allerede i favoritter.";
    }

    // DELETE: http://localhost:8080/api/favorites/stop/{id}
    @DeleteMapping("/stop/{id}")
    public String removeStop(@PathVariable int id) {
        boolean removedStop = favs.removeStopFromFavorites(id);

        if (removedStop) {
            return "Stop " + id + " ble fjernet fra favoritter.";
        }
        return "Stop " + id + " var ikke i favoritter.";
    }

    // GET: http://localhost:8080/api/favorites/stop
    @GetMapping("/stop")
    public List<Stop> getFavoriteStops() {
        try {
            return stopsRepo.getAllStops().stream()
                    .filter(s -> favs.getAllFavoriteStops().contains(s.getStopId()))
                    .toList();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // -------- Ruter add/remove/getAll --------
    // POST: http://localhost:8080/api/favorites/route/{id}
    @PostMapping("/route/{id}")
    public String addRoute(@PathVariable int id) {
        boolean addedRoute = favs.addRouteToFavorites(id);

        if (addedRoute) {
            return "Route " + id + " ble lagt til i favoritter.";
        }
        return "Route " + id + " er allerede i favoritter.";
    }

    // DELETE: http://localhost:8080/api/favorites/route/{id}
    @DeleteMapping("/route/{id}")
    public String removeRoute(@PathVariable int id) {
        boolean removedRoute = favs.removeRouteFromFavorites(id);

        if (removedRoute) {
            return "Route " + id + " ble fjernet fra favoritter.";
        }
        return "Route " + id + " var ikke i favoritter.";
    }

    // GET: http://localhost:8080/api/favorites/route
    @GetMapping("/route")
    public List<Route> getFavoriteRoutes() {
        try {
            return routesRepo.getAllRoutes().stream()
                    .filter(r -> favs.getAllFavoriteRoutes().contains(r.getRouteId()))
                    .toList();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // -------- Busser add/remove/getAll --------
    // POST: http://localhost:8080/api/favorites/bus/{id}
    @PostMapping("/bus/{id}")
    public String addBus(@PathVariable int id) {
        boolean addedBus = favs.addBusToFavorites(id);

        if (addedBus) {
            return "Buss " + id + " ble lagt til i favoritter.";
        }
        return "Buss " + id + " er allerede i favoritter.";
    }

    // DELETE: http://localhost:8080/api/favorites/bus/{id}
    @DeleteMapping("/bus/{id}")
    public String removeBus(@PathVariable int id) {
        boolean removedBus = favs.removeBusFromFavorites(id);

        if (removedBus) {
            return "Buss " + id + " ble fjernet fra favoritter.";
        }
        return "Buss " + id + " var ikke i favoritter.";
    }

    // GET: http://localhost:8080/api/favorites/bus
    @GetMapping("/bus")
    public List<Bus> getFavoriteBuses() {
        try {
            return busesRepo.getAllBuses().stream()
                    .filter(b -> favs.getAllFavoriteBuses().contains(b.getVehicleId()))
                    .toList();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // -------- Turer/Trip add/remove/getAll --------
    // POST: http://localhost:8080/api/favorites/trip/{id}
    @PostMapping("/trip/{id}")
    public String addTrip(@PathVariable int id) {
        boolean addedTrip = favs.addTripToFavorites(id);

        if (addedTrip) {
            return "Trip " + id + " ble lagt til i favoritter.";
        }
        return "Trip " + id + " er allerede i favoritter.";
    }

    // DELETE: http://localhost:8080/api/favorites/trip/{id}
    @DeleteMapping("/trip/{id}")
    public String removeTrip(@PathVariable int id) {
        boolean removedTrip = favs.removeTripFromFavorites(id);

        if (removedTrip) {
            return "Trip " + id + " ble fjernet fra favoritter.";
        }
        return "Trip " + id + " var ikke i favoritter.";
    }

    // GET: http://localhost:8080/api/favorites/trip
    @GetMapping("/trip")
    public List<Trip> getFavoriteTrips() {
        try {
            return tripsRepo.getAllTrips().stream()
                    .filter(t -> favs.getAllFavoriteTrips().contains(t.getTripId()))
                    .toList();
        } catch (RepositoryException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}