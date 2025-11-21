package hiof_project.infrastructure.adapters.api.controller;

import hiof_project.domain.service.TicketService;
import hiof_project.infrastructure.adapters.api.dto.TicketRequestDTO;
import hiof_project.infrastructure.adapters.api.dto.TicketResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Håndterer HTTP-metoder fra klient(f.eks Postman)
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService service;
    public TicketController(TicketService service) {
        this.service = service;
    }

    private Long getUserIdFromSecurity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName());
    }

    // Endepunkt for å opprette billett
    @PostMapping
    public TicketResponseDTO createTicket(@RequestBody TicketRequestDTO request) {
        Long fakeUserId = 1L;
        return service.createTicket(fakeUserId, request);
    }

    // Endepunkt for henting av eksisterende billett
    @GetMapping
    public List<TicketResponseDTO> getMyTickets() {
        Long fakeUserId = 1L;
        return service.getTicketsForUser(fakeUserId);
    }
}
