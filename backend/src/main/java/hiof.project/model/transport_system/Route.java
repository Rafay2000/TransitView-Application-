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

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public ArrayList<Stops> getStops() {
        return stops;
    }

    public void addOneStopToRoute (Stops stop) {
        stops.add(stop);
    }

    public void addMultipleStopsToRoute (ArrayList<Stops> multipleStops) {
        stops.addAll(multipleStops);
    }

    @Override
    public String toString() {
        return getRouteId() + " | " + getRouteName();
    }
}

