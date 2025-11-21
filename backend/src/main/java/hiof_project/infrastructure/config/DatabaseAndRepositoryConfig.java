package hiof_project.infrastructure.config;

import hiof_project.ports.out.*;
import hiof_project.infrastructure.adapters.db.*;

import hiof_project.domain.exception.RepositoryException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseAndRepositoryConfig {

    @Bean
    public Connection azureConnection() throws SQLException {
        String url = System.getenv("AZURE_URL");
        String user = System.getenv("AZURE_DB_USER");
        String password = System.getenv("AZURE_DB_PASSWORD");

        if (url != null && user != null && password != null) {
            System.out.println("Azure Database Connected... (AZURE_URL / AZURE_DB_USER / AZURE_DB_PASSWORD)");
            return DriverManager.getConnection(url, user, password);
        }

        // Hvis det IKKE finnes env-variabler for Azure, bruk H2 som fallback
        System.out.println("Environment variables not found. Switching to H2 fallback.");

        //H2 in-memory database
        String h2Url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        String h2User = "sa";
        String h2Pass = "";

        return DriverManager.getConnection(h2Url, h2User, h2Pass);
    }

    @Bean
    public TripRepository tripRepository(Connection azureConnection) throws RepositoryException {
        return new TripRepositorySQL(azureConnection);
    }

    @Bean
    public StopRepository stopRepository(Connection azureConnection) throws RepositoryException {
        return new StopRepositorySQL(azureConnection);
    }

    @Bean
    public BusRepository busRepository(Connection azureConnection) throws RepositoryException{
        return new BusRepositorySQL(azureConnection);
    }

    @Bean
    public RouteRepository routeRepository(Connection azureConnection) throws RepositoryException {
        return new RouteRepositorySQL(azureConnection);
    }

    @Bean
    public ScheduleTimerRepository scheduleTimerRepository(Connection azureConnection) throws RepositoryException {
        return new ScheduleTimerRepositorySQL(azureConnection);
    }

    @Bean
    public ScheduleRepository scheduleRepository(Connection azureConnection) throws RepositoryException {
        return new ScheduleRepositorySQL(azureConnection);
    }

    @Bean
    public RealtimeSchedulingRepository realtimeSchedulingRepository(Connection azureConnection) throws RepositoryException{
        return new RealtimeSchedulingRepositorySQL(azureConnection);
    }
}

