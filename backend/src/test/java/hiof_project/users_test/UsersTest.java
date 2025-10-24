package hiof_project.users_test;

import hiof_project.domain.model.user_system.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {

    @Test
    void testBruker() {
        Customer bruker = new Customer(1, "testUser", "Test", "User", "Customer");
        assertEquals(1, bruker.getUserId());
        assertEquals("testUser", bruker.getUserName());
        assertEquals("Test", bruker.getFirstName());
        assertEquals("User", bruker.getLastName());
        assertEquals("Customer", bruker.getRole());
        assertEquals("Test User", bruker.getFullName());
    }

    @Test
    void testCustomer() {
        Customer customer = new Customer(1, "johand01", "john.anderson@fakemail.com",
                "password123", "John", "Andersson", "Customer");
        assertEquals(1, customer.getUserId());
        assertEquals("johand01", customer.getUserName());
        assertEquals("John", customer.getFirstName());
        assertEquals("Andersson", customer.getLastName());
        assertEquals("Customer", customer.getRole());
        assertEquals("john.anderson@fakemail.com", customer.getEmail());
        assertEquals("John Andersson", customer.getFullName());
    }

    @Test
    void testAdmin() {
        Admin admin = new Admin(
                2, "brian010", "adminPassword123",
                "Brian", "Hansen", "Admin"
        );

        assertEquals(2, admin.getUserId());
        assertEquals("brian010", admin.getUserName());
        assertEquals("Admin", admin.getRole());
        assertEquals("Brian Hansen", admin.getFullName());
        assertEquals("adminPassword123", admin.getPassword());

        // Admin har ikke email
        assertThrows(NoSuchMethodException.class, () -> {
            admin.getClass().getMethod("getEmail");
        });
    }

    @Test
    void testDeveloper() {
        Developer dev = new Developer(
                3, "dev0101",
                "Lars", "Olsen", "Developer"
        );

        assertEquals(3, dev.getUserId());
        assertEquals("dev0101", dev.getUserName());
        assertEquals("Developer", dev.getRole());
        assertEquals("Lars Olsen", dev.getFullName());

        // Developer har verken email eller password
        assertThrows(NoSuchMethodException.class, () -> {
            dev.getClass().getMethod("getEmail");
        });
        assertThrows(NoSuchMethodException.class, () -> {
            dev.getClass().getMethod("getPassword");
        });
    }
}

