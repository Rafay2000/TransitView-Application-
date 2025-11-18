package hiof_project.database_connection_test;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class AzureConnectionTest {

    @Test
    void testAzureConnectionAndSelect() throws Exception {
        String url = System.getenv("AZURE_URL"); //Hentes fra Microsoft Powershell på PCen, for sikkerhet
        String user = System.getenv("AZURE_DB_USER"); //Hentes fra Microsoft Powershell på PCen, for sikkerhet
        String pass = System.getenv("AZURE_DB_PASSWORD"); //Hentes fra Microsoft Powershell på PCen, for sikkerhet

        //Sjekk at environment variable faktisk er satt opp
        assertNotNull(url, "AZURE_URL is missing – set it as env variable");
        assertNotNull(user, "AZURE_DB_USER is missing – set it as env variable");
        assertNotNull(pass, "AZURE_DB_PASSWORD is missing – set it as env variable");

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            assertNotNull(conn, "Connection is null");
            assertTrue(conn.isValid(5), "Connection to Azure is not valid");

            //Litt ekstra: kjør en enkel query
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT 1");
                assertTrue(rs.next(), "SELECT 1 returned no rows");
            }
        }
    }
}

