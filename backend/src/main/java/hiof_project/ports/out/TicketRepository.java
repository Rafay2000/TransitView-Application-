package hiof_project.ports.out;

import hiof_project.domain.model.ticket_system.Ticket;

import java.util.List;
import java.util.Optional;

// Definerer krav for å lagre/hente billettdata
public interface TicketRepository {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(Long id);
    List<Ticket> findByUserId(Long userId);

}
