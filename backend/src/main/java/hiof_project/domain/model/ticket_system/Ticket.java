package hiof_project.domain.model.ticket_system;

import java.time.LocalDateTime;

// All informasjon en billett inneholder
public record Ticket(
        Long id,
        Long userId,
        String type,
        double price,
        LocalDateTime validFrom,
        LocalDateTime validTo,
        TicketStatus status
) { }
