package hiof_project.infrastructure.adapters.api.dto;

public class BusDTO {

    private final int vehicleId;
    private final String vehicleName;
    private final String vehicleType;
    private final int capacity;

    public BusDTO(int vehicleId, String vehicleName, String vehicleType, int capacity) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
    }

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
}
