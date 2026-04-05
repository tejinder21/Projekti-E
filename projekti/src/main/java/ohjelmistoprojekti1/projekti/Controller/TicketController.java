package ohjelmistoprojekti1.projekti.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ohjelmistoprojekti1.projekti.domain.Ticket;
import ohjelmistoprojekti1.projekti.domain.TicketType;
import ohjelmistoprojekti1.projekti.dto.CreateTicketRequest;
import ohjelmistoprojekti1.projekti.dto.UseTicketRequest;
import ohjelmistoprojekti1.projekti.repository.TicketRepository;
import ohjelmistoprojekti1.projekti.repository.TicketTypeRepository;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;

    public TicketController(TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
    }

    // GET /api/tickets?code=ABC123
    @GetMapping(params = "code")
    public ResponseEntity<Ticket> getTicketByCode(@RequestParam String code) {
        Optional<Ticket> ticket = ticketRepository.findByCode(code);

        if (ticket.isPresent()) {
            return ResponseEntity.ok(ticket.get());
        }

        return ResponseEntity.notFound().build();
    }

    // GET /api/tickets
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // POST /api/tickets
    @PostMapping
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateTicketRequest req) {
        TicketType tt = ticketTypeRepository.findById(req.getTicketTypeId()).orElse(null);

        if (tt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("TicketType not found: " + req.getTicketTypeId());
        }

        Ticket ticket = new Ticket();
        ticket.setTicketType(tt);
        ticket.setCode(req.getCode());
        ticket.setStatus(req.getStatus());
        ticket.setUsedAt(null);

        Ticket saved = ticketRepository.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PATCH /api/tickets/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<?> markTicketAsUsed(@PathVariable Long id, @RequestBody UseTicketRequest req) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);

        if (optionalTicket.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticket = optionalTicket.get();

        if ("USED".equals(ticket.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ticket is already used");
        }

        ticket.setStatus("USED");

        if (req != null && req.getUsed() != null) {
            ticket.setUsedAt(req.getUsed());
        } else {
            ticket.setUsedAt(LocalDateTime.now());
        }

        Ticket updated = ticketRepository.save(ticket);
        return ResponseEntity.ok(updated);
    }
}