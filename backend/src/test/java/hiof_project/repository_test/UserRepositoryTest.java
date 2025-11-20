package hiof_project.repository_test;

import hiof_project.domain.model.user_system.Customer;
import hiof_project.domain.model.user_system.User;
import hiof_project.domain.model.user_system.Role;
import hiof_project.ports.out.RoleRepository;
import hiof_project.ports.out.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testSaveAndFindByEmail() {
        // Arrange
        Role role = new Customer();
        roleRepository.save(role);
        User user = new User("test@example.com", "hashedPass123", role);
        userRepository.save(user);

        // Act
        Optional<User> found = userRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(found.isPresent(), "Brukeren skal finnes i databasen");
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void testFindByEmail_UserNotFound() {
        // Act
        Optional<User> found = userRepository.findByEmail("notfound@example.com");

        // Assert
        assertFalse(found.isPresent(), "Brukeren skal ikke finnes");
    }
    @Test
    void testUniqueEmail() {
        Role role = new Customer();
        roleRepository.save(role);

        User user1 = new User("test@example.com", "password", role);
        userRepository.save(user1);

        User user2 = new User("test@example.com", "password", role);

        assertThrows(Exception.class, () -> userRepository.save(user2));
    }
    @Test
    void testUserRoleSavedCorrectly() {
        Role role = new Customer();
        roleRepository.save(role);

        User user = new User("test@example.com", "password", role);
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals("CUSTOMER", found.get().getRole().getName());
    }


}
