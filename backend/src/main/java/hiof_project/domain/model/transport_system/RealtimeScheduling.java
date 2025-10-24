package hiof_project.domain.model.transport_system;

import java.time.LocalTime;

public class RealtimeScheduling {

    private int realtimeId;             // PK i databasen
    private int tripId;         // FK til Trip
    private int stopId;         // FK til Stop
    private LocalTime updatedDeparture;
    private LocalTime updatedArrival;

    // Konstruktør for sanntid oppdatering.
    public RealtimeScheduling(int realtimeIdid, int tripId, int stopId, LocalTime updatedDeparture, LocalTime updatedArrival) {
        this.realtimeId = realtimeIdid;
        this.tripId = tripId;
        this.stopId = stopId;
        this.updatedDeparture = updatedDeparture;
        this.updatedArrival = updatedArrival;
    }

    // Gettere og settere
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

    public void setRealtimeId(int realtimeId) {
        this.realtimeId = realtimeId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public void setUpdatedDeparture(LocalTime updatedDeparture) {
        this.updatedDeparture = updatedDeparture;
    }

    public void setUpdatedArrival(LocalTime updatedArrival) {
        this.updatedArrival = updatedArrival;
    }

    @Override
    public String toString() {
        return "TripID: " + tripId + " | StopID: " + stopId +
                " | Avgang: " + (updatedDeparture != null ? updatedDeparture : "N/A") +
                " | Ankomst: " + (updatedArrival != null ? updatedArrival : "N/A");
    }
}

