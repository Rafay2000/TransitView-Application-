package hiof_project.infrastructure.adapters.api.dto;

public class TripDTO {

    private final int tripId;
    private final RouteDTO route;
    private final ScheduleDTO schedule;
    private final BusDTO bus;

    public TripDTO(int tripId, RouteDTO route, ScheduleDTO schedule, BusDTO bus) {
        this.tripId = tripId;
        this.route = route;
        this.schedule = schedule;
        this.bus = bus;
    }

    public int getTripId() {
        return tripId;
    }

    public RouteDTO getRoute() {
        return route;
    }

    public ScheduleDTO getSchedule() {
        return schedule;
    }

    public BusDTO getBus() {
        return bus;
    }
}
