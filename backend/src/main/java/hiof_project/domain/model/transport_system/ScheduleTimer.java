package hiof_project.domain.model.transport_system;

import java.time.LocalTime;

public class ScheduleTimer {

    private Integer scheduleTimerId;
    private LocalTime arrival;
    private LocalTime departure;

    //Konstruktør for backend hardkoding
    public ScheduleTimer(LocalTime arrival, LocalTime departure) {
        this.arrival = arrival;
        this.departure = departure;
    }

    //Konstruktør som kan brukes til å genere ID i database
    public ScheduleTimer(Integer scheduleTimerId, LocalTime arrival, LocalTime departure) {
        this.scheduleTimerId = scheduleTimerId;
        this.arrival = arrival;
        this.departure = departure;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalTime arrival) {
        this.arrival = arrival;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }

    @Override
    public String toString() {
        return "Ankomst: " + (arrival != null ? arrival : "N/A") +
                " / Avreise: " + (departure != null ? departure : "N/A");
    }
}

