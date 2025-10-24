package hiof_project.ports.out;

import hiof_project.domain.exception.RepositoryException;
import hiof_project.domain.model.User;
import hiof_project.domain.model.transport_system.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

// Interface for kommunisering med databasen. Brukes CRUD operasjoner
public interface UserRepository extends JpaRepository<User, Long> {

    void createUser(User user) throws RepositoryException; //opprette bruker
    void saveUser(User user) throws RepositoryException; //lagre bruker
    void updateUser(User user) throws RepositoryException; //oppdater bruker
    void deleteUser(User user) throws RepositoryException; //slett bruker basert på IDen
    Optional<Route> getByUserId(int userId) throws RepositoryException; //hent bruker basert på IDen
    ArrayList<Route> getAllUsers() throws RepositoryException; //hent alle brukere fra db

    // Metode som finner bruker basert på epost.
    Optional<User> findByEmail(String email);
}
