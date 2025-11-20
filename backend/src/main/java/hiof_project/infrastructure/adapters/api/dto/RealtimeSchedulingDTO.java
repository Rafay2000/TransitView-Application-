package hiof_project.infrastructure.adapters.api.dto;

import java.time.LocalTime;

public class RealtimeSchedulingDTO {

    private final int realtimeId;
    private final int tripId;
    private final int stopId;
    private final LocalTime updatedDeparture;
    private final LocalTime updatedArrival;
    private final String status;

    public RealtimeSchedulingDTO(int realtimeId, int tripId, int stopId,
                                 LocalTime updatedDeparture, LocalTime updatedArrival, String status) {
        this.realtimeId = realtimeId;
        this.tripId = tripId;
        this.stopId = stopId;
        this.updatedDeparture = updatedDeparture;
        this.updatedArrival = updatedArrival;
        this.status = status;
    }

    public int getRealtimeId() {
        return realtimeId;
    }

    public int getTripId() {
        return tripId;
    }

    public int getStopId() {
        return stopId;
    }

    public LocalTime getUpdatedDeparture() {
        return updatedDeparture;
    }

    public LocalTime getUpdatedArrival() {
        return updatedArrival;
    }

    public String status() {
        return status;
    }
}
