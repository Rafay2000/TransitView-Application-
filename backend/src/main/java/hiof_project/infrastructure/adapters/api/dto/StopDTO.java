package hiof_project.infrastructure.adapters.api.dto;

public class StopDTO {

    private final int stopId;
    private final String stopName;
    private final double distanceInKm;
    private final int timeToNextStop;
    private final String descriptionNextStop;

    public StopDTO(int stopId, String stopName, double distanceInKm, int timeToNextStop, String descriptionNextStop) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.distanceInKm = distanceInKm;
        this.timeToNextStop = timeToNextStop;
        this.descriptionNextStop = descriptionNextStop;
    }

    public int getStopId() {
        return stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }

    public int getTimeToNextStop() {
        return timeToNextStop;
    }

    public String getDescriptionNextStop() {
        return descriptionNextStop;
    }
}
