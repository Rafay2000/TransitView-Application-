package hiof_project.infrastructure.adapters.api.dto;

import java.util.ArrayList;

public class RouteDTO {

    private final int routeId;
    private final String routeName;
    private final ArrayList<StopDTO> stops;

    public RouteDTO(int routeId, String routeName, ArrayList<StopDTO> stops) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.stops = stops;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public ArrayList<StopDTO> getStops() {
        return stops;
    }
}
