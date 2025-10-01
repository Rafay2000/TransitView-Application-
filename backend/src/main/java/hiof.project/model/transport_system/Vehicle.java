package hiof.project.model.transport_system;

public abstract class Vehicle {

    private final int vehicleId;
    private String vehicleName;
    private String vehicleType;
    private int capacity;


    public Vehicle(int vehicleId, String vehicleName, String vehicleType, int capacity) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
    }

    //Setters
    public int getVehicleId() {
        return vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public int getCapacity() {
        return capacity;
    }

    //Setters
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    //Display full vehicle info for debugging, testing and verification
    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + vehicleId +
                ", name='" + vehicleName + '\'' +
                ", capacity=" + capacity +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
