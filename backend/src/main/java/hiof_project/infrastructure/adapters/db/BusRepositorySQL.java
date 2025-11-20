package hiof_project.infrastructure.adapters.db;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.transport_system.Bus;
import hiof_project.ports.out.BusRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class BusRepositorySQL implements BusRepository {

    private Connection connection;

    public BusRepositorySQL(Connection connection) {
        this.connection = connection;
    }

    //Oppretter buss verdier basert på datatyper fra Bus klassen og legger det i SQL databasen
    @Override
    public void createBus(Bus bus) throws RepositoryException {
        String sql = "INSERT INTO Buses (bus_id, bus_name, bus_type, capacity) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bus.getVehicleId());
            preparedStatement.setString(2, bus.getVehicleName());
            preparedStatement.setString(3, bus.getVehicleType());
            preparedStatement.setInt(4, bus.getCapacity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not create bus in database", e);
        }
    }

    // Optional-style effektiv måte: hvis ID finnes i DB. Så update, ellers create
    @Override
    public void saveBus(Bus bus) throws RepositoryException {
        Optional<Bus> existing = getByBusId(bus.getVehicleId());

        if (existing.isPresent()) {
            updateBus(bus);
        } else {
            createBus(bus);
        }
    }

    //Oppdaterer buss verdier på et bestemt buss id fra SQL tabellen
    @Override
    public void updateBus(Bus bus) throws RepositoryException {
        String sql = "UPDATE Buses SET bus_name = ?, bus_type = ?, capacity = ? WHERE bus_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bus.getVehicleName());
            preparedStatement.setString(2, bus.getVehicleType());
            preparedStatement.setInt(3, bus.getCapacity());
            preparedStatement.setInt(4, bus.getVehicleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not save bus in database", e);
        }

    }

    //Sletter buss verdier basert på datatyper fra Bus klassen og legger det i SQL databasen
    @Override
    public void deleteBusId(int vehicleId) throws RepositoryException {
        String sql = "DELETE FROM Buses WHERE bus_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, vehicleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not delete bus with matching ID " + vehicleId, e);
        }

    }

    //Henter et bestemt buss id fra bus tabellen i SQL databasen
    @Override
    public Optional<Bus> getByBusId(int vehicleId) throws RepositoryException {
        String sql = "SELECT * FROM Buses WHERE bus_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, vehicleId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Bus bus = new Bus(
                        resultSet.getInt("bus_id"),
                        resultSet.getString("bus_name"),
                        resultSet.getString("bus_type"),
                        resultSet.getInt("capacity")
                );
                return Optional.of(bus);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RepositoryException("Could not retrieve bus with id " + vehicleId, e);
        }
    }

    //Henter alle buss id fra bus tabellen i SQL databasen
    @Override
    public ArrayList<Bus> getAllBuses() throws RepositoryException {
        String sql = "SELECT bus_id, bus_name, bus_type, capacity FROM Buses";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ArrayList<Bus> busList = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int vehicleId = resultSet.getInt("bus_id");
                String vehicleName = resultSet.getString("bus_name");
                String vehicleType = resultSet.getString("bus_type");
                int capacity = resultSet.getInt("capacity");

                Bus bus = new Bus(vehicleId, vehicleName, vehicleType, capacity);
                busList.add(bus);
            }

            return busList;

        } catch (SQLException e) {
            throw new RepositoryException("ERROR: Could not retrieve buses from the database", e);
        }
    }
}


