package hiof.project.model.transport_system;

public abstract class Vehicle {

    private int vehicleId;
    private String vehicleName;
    private String vehicleType;
    private int capacity;


    public Vehicle(int vehicleId, String vehicleName, String vehicleType, int capacity) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleId(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

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
