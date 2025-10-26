package hiof_project.domain.service;

import hiof_project.domain.model.user_system.Role;
import hiof_project.domain.model.user_system.User;
import hiof_project.ports.out.RoleRepository;
import hiof_project.ports.out.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// @Service gjør at Spring kan bruke klassen automatisk.
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Spring setter inn repository og encoder.
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Funksjon som registrerer bruker med epost og passord.
    public void register(String email, String password) {
        // E-mail checker
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Ugyldig e-postadresse");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-postadressen er allerede registrert");
        }
        // Password checker
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

        String hashedPassword = passwordEncoder.encode(password);
        Role defaultRole = roleRepository.findByName("CUSTOMER");

        // Oppretter bruker med CUSTOMER som basis-rolle
        User user = new User(email, hashedPassword, defaultRole);
        userRepository.save(user);
    }

    // Funksjon som sjekker om epost og passord er registrert.
    public boolean login(String email, String password) {

        // .map kjøres kun dersom brukeren finnes, hvis ikke returneres false.
        return userRepository.findByEmail(email)
                .map(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .orElse(false);
    }
}
