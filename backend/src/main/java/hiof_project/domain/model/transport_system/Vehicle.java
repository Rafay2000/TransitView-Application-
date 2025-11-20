package hiof_project.domain.model.transport_system;

//Absrakt klasse for en kjøretøy (per nå er det buss som er hovedmålet, evt annet som trikk, tog kan legges til senere)
public abstract class Vehicle {

    private final int vehicleId;
    private String vehicleName;
    private String vehicleType;
    private int capacity;

    //Kontruktør.
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
