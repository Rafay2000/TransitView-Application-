package hiof_project.infrastructure.adapters.api.dto;

import java.time.LocalTime;

public class ScheduleTimerDTO {

    private final Integer scheduleTimerId;
    private final LocalTime arrival;
    private final LocalTime departure;

    public ScheduleTimerDTO(Integer scheduleTimerId, LocalTime arrival, LocalTime departure) {
        this.scheduleTimerId = scheduleTimerId;
        this.arrival = arrival;
        this.departure = departure;
    }

    public Integer getScheduleTimerId() {
        return scheduleTimerId;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    public LocalTime getDeparture() {
        return departure;
    }
}