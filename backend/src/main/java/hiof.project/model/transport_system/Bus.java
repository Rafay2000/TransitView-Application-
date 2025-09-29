package hiof.project.model.transport_system;

public class Bus extends Vehicle {

    public Bus (int vehicleId, String vehicleName, String vehicleType, int capacity) {
        super(vehicleId, vehicleName, vehicleType, capacity);
    }

    @Override
    public String toString() {
        return getVehicleName() + " - BusType: " + getVehicleType() + " - " + getCapacity() + " seter";
    }
}
