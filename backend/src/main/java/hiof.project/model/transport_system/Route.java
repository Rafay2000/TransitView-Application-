package hiof.project.model.transport_system;

import java.util.ArrayList;

public class Route {

    private int routeId;
    private String routeName;
    private ArrayList<Stops> stops;

    public Route(int routeId, String routeName, ArrayList<Stops> stops) {
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

    public ArrayList<Stops> getStops() {
        return stops;
    }

    //Setters
    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    //Add one individual bus stop to the list
    public void addOneStopToRoute (Stops stop) {
        stops.add(stop);
    }

    //Add multiple bus stops to the list
    public void addMultipleStopsToRoute (ArrayList<Stops> multipleStops) {
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

