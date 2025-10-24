package hiof_project.ports.in;

import hiof_project.domain.service.AuthService;
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

    // Leser epost og passord fra JSON.
    record AuthRequest(String email, String password) {}

    // Endepunkt registrering.
    @PostMapping("/register")
    public String register(@RequestBody AuthRequest req) {
        authService.register(req.email(), req.password());
        return "Bruker registrert";
    }

    // Endepunkt innlogging.
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest req) {
        boolean accepted = authService.login(req.email(), req.password());

        if (accepted) {
            return "Vellykket innlogging!";
        } else {
            return "Feil epost eller passord";
        }
    }
}
