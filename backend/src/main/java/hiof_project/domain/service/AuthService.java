package hiof_project.domain.service;

import hiof_project.domain.model.User;
import hiof_project.ports.out.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// @Service gjør at Spring kan bruke klassen automatisk.
@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    // Spring setter inn repository og encoder.
    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    // Funksjon som registrerer bruker med epost og passord.
    public void register(String email, String password) {
        // E-mail checker
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Ugyldig e-postadresse");
        }
        if (repo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-postadressen er allerede registrert");
        }
        // Passord checker
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Passord må inneholde minst 8 tegn");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Passord må inneholde minst én stor bokstav");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Passord må inneholde minst én liten bokstav");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Passord må inneholde minst ett tall");
        }

        // Oppretter bruker dersom ingen if "aktiveres"
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(password));
        repo.save(u);
    }

    // Funksjon som sjekker om epost og passord er registrert.
    public boolean login(String email, String password) {

        // .map kjøres kun dersom brukeren finnes, hvis ikke returneres false.
        return repo.findByEmail(email)
                .map(u -> encoder.matches(password, u.getPasswordHash()))
                .orElse(false);
    }
}
