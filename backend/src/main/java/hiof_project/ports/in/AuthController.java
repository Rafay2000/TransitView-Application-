package hiof_project.ports.in;

import hiof_project.infrastructure.adapters.api.dto.LoginDTO;
import hiof_project.infrastructure.adapters.api.dto.RegisterDTO;
import hiof_project.domain.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController returnerer tekst direkte til frontend.
@RestController

// Alt som starter med /api/auth går hit
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;

    // Spring gir et automatisk AuthService-objekt.
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endepunkt registrering.
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO dto) {
        authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Bruker er registrert");
    }

    // Endepunkt innlogging.
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        try {
            boolean accepted = authService.login(dto.email(), dto.password());
            return ResponseEntity.ok("Innlogging vellykket");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }
}
