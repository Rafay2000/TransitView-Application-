package java.hiof_project.domain.model.transport_system;

public class Bus extends Vehicle {

    //Bus constructor
    public Bus (int vehicleId, String vehicleName, String vehicleType, int capacity) {
        super(vehicleId, vehicleName, vehicleType, capacity);
    }

    //Display bus info to the user
    @Override
    public String toString() {
        return getVehicleName() + " | Buss type: " + getVehicleType() + " | " + getCapacity() + " seter";
    }

    //Display bus info for debugging, testing and verification
    public String fullVehicleInfo() {
        return super.toString();
    }
}
