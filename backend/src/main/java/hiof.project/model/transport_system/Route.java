package hiof.project.model.transport_system;

import java.util.ArrayList;

public class Route {

    private final int routeId;
    private String routeName;
    private ArrayList<Stop> stops;

    public Route(int routeId, String routeName, ArrayList<Stop> stops) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.stops = stops;
    }

    //Getters
    public int getRouteId() {
        return routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }

    //Setters
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    //Add one individual bus stop to the list
    public void addOneStopToRoute (Stop stop) {
        stops.add(stop);
    }

    //Add multiple bus stops to the list
    public void addMultipleStopsToRoute (ArrayList<Stop> multipleStops) {
        stops.addAll(multipleStops);
    }

    //Display route info to the user
    @Override
    public String toString() {
        return "Rute Nummer " + routeId + " | " + routeName;
    }

    //Display route info for debugging, testing and verification
    public String fullRouteInfo() {
        return routeId + " | " + routeName + " | Stops: " + stops;
    }
}

