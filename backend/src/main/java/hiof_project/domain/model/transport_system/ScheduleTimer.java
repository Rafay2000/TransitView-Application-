package hiof_project.domain.model.transport_system;

import java.time.LocalTime;

//Klasse som håndterer ankomst og avreise tider per bussholdeplass
public class ScheduleTimer {

    private Integer scheduleTimerId; //PK i ScheduleTimers tabellen SQL
    private Integer scheduleId; // FK til Schedule tabellen i SQL
    private Integer sequence; //Rekkefølge på stoppet, følgewr SQL oppsettet
    private LocalTime arrival;
    private LocalTime departure;

    //Konstruktør for backend hardkoding
    public ScheduleTimer(LocalTime arrival, LocalTime departure) {
        this.arrival = arrival;
        this.departure = departure;
    }

    //Konstruktør som kan brukes når ID verdi er satt (fra databasen)
    public ScheduleTimer(Integer scheduleTimerId, LocalTime arrival, LocalTime departure) {
        this.scheduleTimerId = scheduleTimerId;
        this.arrival = arrival;
        this.departure = departure;
    }

    // Full konstruktør (brukes når man vil sette alt)
    public ScheduleTimer(Integer scheduleTimerId, Integer scheduleId, Integer sequence,
                         LocalTime arrival, LocalTime departure) {
        this.scheduleTimerId = scheduleTimerId;
        this.scheduleId = scheduleId;
        this.sequence = sequence;
        this.arrival = arrival;
        this.departure = departure;
    }

    public Integer getScheduleTimerId() { return scheduleTimerId; }

    public void setScheduleTimerId(Integer scheduleTimerId) { this.scheduleTimerId = scheduleTimerId; }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

