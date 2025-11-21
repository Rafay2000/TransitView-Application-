package hiof_project.domain.service;

import hiof_project.domain.model.ticket_system.Ticket;
import hiof_project.domain.model.ticket_system.TicketStatus;
import hiof_project.infrastructure.adapters.api.dto.TicketRequestDTO;
import hiof_project.infrastructure.adapters.api.dto.TicketResponseDTO;
import hiof_project.ports.out.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Håndterer billett-operasjoner
@Service
public class TicketService {
    private final TicketRepository repository;
    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    // Oppretter ny billet mot en bruker
    public TicketResponseDTO createTicket(Long userId, TicketRequestDTO request) {
        Ticket ticket = new Ticket(
                null,
                userId,
                request.type(),
                calculatePrice(request.type()),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                TicketStatus.ACTIVE
        );
        Ticket saved = repository.save(ticket);
        return TicketResponseDTO.from(saved);
    }

    // Henter eksisterende billetter
    public List<TicketResponseDTO> getTicketsForUser(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(TicketResponseDTO::from)
                .toList();
    }

    // Kalkulerer pris basert på billettype
    private double calculatePrice(String type) {
        return switch(type.toLowerCase()) {
            case "student" -> 30;
            case "honnør" -> 20;
            case "månedskort" -> 450;
            default -> 45;
        };
    }
}
