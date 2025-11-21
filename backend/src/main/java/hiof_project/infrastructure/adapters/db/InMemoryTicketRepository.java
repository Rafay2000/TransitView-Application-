package hiof_project.infrastructure.adapters.db;

import hiof_project.domain.model.ticket_system.Ticket;
import hiof_project.ports.out.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

// Repository for å simulere en database ved bruk av HashMap
@Repository
public class InMemoryTicketRepository implements TicketRepository {
    // Simulerer databasen
    private final Map<Long, Ticket> tickets = new HashMap<>();
    // Genererer unik ID
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Ticket save(Ticket ticket) {
        // Ny billet = ny id, ellers behold id
        Long id = ticket.id() == null ? idCounter.getAndIncrement() : ticket.id();
        Ticket saved = new Ticket(
                id,
                ticket.userId(),
                ticket.type(),
                ticket.price(),
                ticket.validFrom(),
                ticket.validTo(),
                ticket.status()
        );

        tickets.put(id, saved);
        return saved;
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return Optional.ofNullable(tickets.get(id));
    }

    @Override
    public List<Ticket> findByUserId(Long userId) {
        return tickets.values().stream()
                .filter(t -> t.userId().equals(userId))
                .toList();
    }
}
