package hiof_project.ports.out;

import hiof_project.domain.model.user_system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    // Metode som finner bruker basert på epost.
    Optional<User> findByEmail(String email);
}
