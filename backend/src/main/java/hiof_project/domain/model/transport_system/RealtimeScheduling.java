package hiof_project.domain.model.transport_system;

import java.time.LocalTime;

//Klasse for sanntidsoppdatering av ankosmt og avreiser på et eller flere bussholdeplass
public class RealtimeScheduling {

    private int realtimeId;             // PK i databasen
    private int tripId;         // FK til Trip
    private int stopId;         // FK til Stop
    private LocalTime updatedArrival;
    private LocalTime updatedDeparture;
    private String status;

    //Konstruktør uten realtimeId (brukes når vi lager ny oppføring uten å angi IDen)
    public RealtimeScheduling(int tripId, int stopId,
                              LocalTime updatedArrival, LocalTime updatedDeparture, String status) {
        this.tripId = tripId;
        this.stopId = stopId;
        this.updatedArrival = updatedArrival;
        this.updatedDeparture = updatedDeparture;
        this.status = status;
    }

    //Konstruktør for sanntid oppdatering. Angi IDen
    public RealtimeScheduling(int realtimeId, int tripId, int stopId,
                              LocalTime updatedArrival, LocalTime updatedDeparture, String status) {
        this.realtimeId = realtimeId;
        this.tripId = tripId;
        this.stopId = stopId;
        this.updatedArrival = updatedArrival;
        this.updatedDeparture = updatedDeparture;
        this.status = status;
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

    public LocalTime getUpdatedArrival() {
        return updatedArrival;
    }

    public LocalTime getUpdatedDeparture() {
        return updatedDeparture;
    }

    public String getStatus() {
        return status;
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

    public void setUpdatedArrival(LocalTime updatedArrival) {
        this.updatedArrival = updatedArrival;
    }

    public void setUpdatedDeparture(LocalTime updatedDeparture) {
        this.updatedDeparture = updatedDeparture;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TripID: " + tripId + " | StopID: " + stopId +
                " | Avgang: " + (updatedDeparture != null ? updatedDeparture : "N/A") +
                " | Ankomst: " + (updatedArrival != null ? updatedArrival : "N/A");
    }
}

