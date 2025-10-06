package hiof_project.model.transport_system;

public class Stop {

    private final int stopId;
    private String stopName;
    private double distanceInKm;
    private int timeToNextStop;
    private String descriptionNextStop;

    public Stop(int stopId, String stopName) {
        this.stopId = stopId;
        this.stopName = stopName;
    }

    public Stop(int stopId, String stopName, double distanceInKm, int timeToNextStop, String descriptionNextStop) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.distanceInKm = distanceInKm;
        this.timeToNextStop = timeToNextStop;
        this.descriptionNextStop = descriptionNextStop;
    }

    public String getStopNameName() {
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

    public void setDistanceInKm(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public void setTimeToNextStop(int timeToNextStop) {
        this.timeToNextStop = timeToNextStop;
    }

    public void setDescription(String descriptionNextStop) {
        this.descriptionNextStop = descriptionNextStop;
    }

    // Placeholder method to calculate time till next stop (sprint 2 feature)
    public int calculateTimeToNext() {
        return 0;
    }

    @Override
    public String toString() {
        String desc = (descriptionNextStop != null && !descriptionNextStop.isEmpty()) ? "\""
                + descriptionNextStop + "\"" : "Ikke tilgjengelig stopplass info";
        return stopName + "\n"
                + "**(Estimert kjøretur " + timeToNextStop + " min / "
                + distanceInKm + " KM avstand / " + desc + ")**";
    }


    public String fullStopInfo() {
        return "Stop{" +
                "stopId=" + stopId +
                ", stopName='" + stopName + '\'' +
                ", distanceInKm=" + distanceInKm +
                ", timeToNextStop=" + timeToNextStop +
                ", description='" + descriptionNextStop + '\'' +
                '}';
    }
}
