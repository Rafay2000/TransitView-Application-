package hiof_project.infrastructure.config;

import hiof_project.ports.out.StopRepository;
import hiof_project.ports.out.TripRepository;
import hiof_project.ports.out.ScheduleTimerRepository;

import hiof_project.infrastructure.adapters.db.StopRepositorySQL;
import hiof_project.infrastructure.adapters.db.TripRepositorySQL;
import hiof_project.infrastructure.adapters.db.ScheduleTimerRepositorySQL;

import hiof_project.domain.exception.RepositoryException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class RepositoryConfig {

    @Bean
    public Connection azureConnection() throws SQLException {
        String url = System.getenv("AZURE_URL");
        String user = System.getenv("AZURE_DB_USER");
        String pass = System.getenv("AZURE_DB_PASSWORD");

        if (url == null || user == null || pass == null) {
            throw new SQLException("Azure env vars not set (AZURE_URL / AZURE_DB_USER / AZURE_DB_PASSWORD)");
        }

        return DriverManager.getConnection(url, user, pass);
    }

    @Bean
    public TripRepository tripRepository(Connection azureConnection) {
        return new TripRepositorySQL(azureConnection);
    }

    @Bean
    public StopRepository stopRepository(Connection azureConnection) {
        return new StopRepositorySQL(azureConnection);
    }

    @Bean
    public ScheduleTimerRepository scheduleTimerRepository(Connection azureConnection) {
        return new ScheduleTimerRepositorySQL(azureConnection);
    }
}

