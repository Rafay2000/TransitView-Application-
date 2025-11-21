package hiof_project.domain.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class FavoriteService {

    private final ArrayList<Integer> favoriteStop  = new ArrayList<>();
    private final ArrayList<Integer> favoriteRoute = new ArrayList<>();
    private final ArrayList<Integer> favoriteBus   = new ArrayList<>();
    private final ArrayList<Integer> favoriteTrip  = new ArrayList<>();

    // --- Bussholdeplasser favoritt metoder ---
    public boolean addStopToFavorites(int id) {
        if (!favoriteStop.contains(id)) {
            favoriteStop.add(id);
            return true;
        }
        return false;
    }

    public boolean removeStopFromFavorites(int id) {
        return favoriteStop.remove((Integer) id); // true hvis fjernet
    }

    public ArrayList<Integer> getAllFavoriteStops() {
        return favoriteStop;
    }


    // ---- Ruter favoritt metoder ----
    public boolean addRouteToFavorites(int id) {
        if (!favoriteRoute.contains(id)) {
            favoriteRoute.add(id);
            return true;
        }
        return false;
    }

    public boolean removeRouteFromFavorites(int id) {
        return favoriteRoute.remove((Integer) id); // true hvis fjernet
    }

    public ArrayList<Integer> getAllFavoriteRoutes() {
        return favoriteRoute;
    }


    // ---- Buss favoritt metoder ----
    public boolean addBusToFavorites(int id) {
        if (!favoriteBus.contains(id)) {
            favoriteBus.add(id);
            return true;
        }
        return false;
    }

    public boolean removeBusFromFavorites(int id) {
        return favoriteBus.remove((Integer) id); // true hvis fjernet
    }

    public ArrayList<Integer> getAllFavoriteBuses() {
        return favoriteBus;
    }


    // ---- Tur favoritt metoder ----
    public boolean addTripToFavorites(int id) {
        if (!favoriteTrip.contains(id)) {
            favoriteTrip.add(id);
            return true;
        }
        return false;
    }

    public boolean removeTripFromFavorites(int id) {
        return favoriteTrip.remove((Integer) id); // true hvis fjernet
    }

    public ArrayList<Integer> getAllFavoriteTrips() {
        return favoriteTrip;
    }
}

