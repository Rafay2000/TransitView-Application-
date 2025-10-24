package hiof_project.infrastructure.adapters;

import hiof_project.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Interface for kommunisering med db.
public interface UserRepository extends JpaRepository<User, Long> {

    // Metode som finner bruker basert på epost.
    Optional<User> findByEmail(String email);
}
