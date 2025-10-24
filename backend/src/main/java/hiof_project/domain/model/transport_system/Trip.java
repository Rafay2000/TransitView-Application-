package hiof_project.domain.model.transport_system;

public class Trip {

    private int tripId;
    private Route route;
    private Schedule schedule;
    private Bus bus;

    public Trip(int tripId, Route route, Schedule schedule, Bus bus) {
        this.tripId = tripId;
        this.route = route;
        this.schedule = schedule;
        this.bus = bus;
    }

    // Getters
    public int getTripId() { return tripId; }
    public Route getRoute() { return route; }
    public Schedule getSchedule() { return schedule; }
    public Bus getBus() { return bus; }


    // Formatert oversikt for bruker
    @Override
    public String toString() {
        // Header
        String output = route.toString().replace("\n", " ") + "| " + schedule.toString() + "\n";
        output += "| " + bus.toString() + " |\n";
        output += "--------------------------------------------------\n";

        var stops = route.getStops();
        var timers = schedule.getScheduleTimer();

        for (int i = 0; i < stops.size(); i++) {
            Stop s = stops.get(i);
            ScheduleTimer timer = timers.get(i);

            String arrival = (timer.getArrival() != null) ? timer.getArrival().toString() : "N/A";
            String departure = (timer.getDeparture() != null) ? timer.getDeparture().toString() : "N/A";
            String nextStop = (i + 1 < stops.size()) ? stops.get(i + 1).getStopName() : stops.get(0).getStopName();

            output += "Ankomst: " + arrival + " på " + s.getStopName() +
                    " | Avreise: " + departure + " - til " + nextStop + "\n";

            output += "**(Estimert kjøretur til neste stopp: " +
                    s.getTimeToNextStop() + " min / " +
                    s.getDistanceInKm() + " KM avstand / \"" +
                    s.getDescriptionNextStop() + "\")**\n";
        }

        return output;
    }

    // For debugging / testing
    public String fullTripInfo() {
        return "Trip{" +
                "tripId=" + tripId +
                ", route=" + route.fullRouteInfo() +
                ", schedule=" + schedule.getDefinedDate() +
                ", bus=" + bus.fullVehicleInfo() +
                '}';
    }
}

