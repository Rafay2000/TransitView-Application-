package hiof_project.domain.service;

import hiof_project.infrastructure.adapters.api.dto.RegisterDTO;
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
    public void register(RegisterDTO dto) {
        UserValidator.validateEmail(dto.email());
        UserValidator.validatePassword(dto.password());

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("E-post er allerede i bruk");
        }

        String hashedPassword = passwordEncoder.encode(dto.password());
        Role defaultRole = roleRepository.findByName("CUSTOMER");

        // Oppretter bruker med CUSTOMER som basis-rolle
        User user = new User(dto.email(), hashedPassword, defaultRole);
        userRepository.save(user);
    }

    // Funksjon som sjekker om epost og passord er registrert.
    public boolean login(String email, String password) {

        // Felles melding for ugyldig e-post eller passord, dette er bedre for sikkerhet mot user enumeration
        String errorMessage = "Feil e-post eller passord";

        // Sjekker om epost eksisterer i databasen
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(errorMessage));

        // Sjekker om passord er gyldig
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException(errorMessage);
        }
        return true;
    }
}

