package hiof_project.infrastructure.adapters.api.dto;

import hiof_project.domain.model.ticket_system.Ticket;
import hiof_project.domain.model.ticket_system.TicketStatus;

import java.time.LocalDateTime;

// DTO for ut-data til klient
public record TicketResponseDTO(
        Long id,
        String type,
        double price,
        LocalDateTime validFrom,
        LocalDateTime validTo,
        TicketStatus status
) {
    // Konverterer ticket til en DTO for respons.
    public static TicketResponseDTO from(Ticket ticket) {
        return new TicketResponseDTO(
                ticket.id(),
                ticket.type(),
                ticket.price(),
                ticket.validFrom(),
                ticket.validTo(),
                ticket.status()
        );
    }
}
