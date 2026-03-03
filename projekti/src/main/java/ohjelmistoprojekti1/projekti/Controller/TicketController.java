package ohjelmistoprojekti1.projekti.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ohjelmistoprojekti1.projekti.domain.Ticket;
import ohjelmistoprojekti1.projekti.repository.TicketRepository;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // GET /api/tickets/{code} - Hae lippu sen koodin perusteella (ovella tarkastuksessa)
    @GetMapping("/{code}")
    public ResponseEntity<Ticket> getTicketByCode(@PathVariable String code) {
        Optional<Ticket> ticket = ticketRepository.findByCode(code);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /api/tickets - Hae kaikki liput (vaatii filterin status perusteella)
    @GetMapping
    public List<Ticket> getAllTickets(@RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            return ticketRepository.findByStatus(status);
        }
        return ticketRepository.findAll();
    }

    // PUT /api/tickets/{code}/use - Merkitse lippu käytetyksi
    @PutMapping("/{code}/use")
    public ResponseEntity<Ticket> useTicket(@PathVariable String code) {
        Optional<Ticket> ticketOpt = ticketRepository.findByCode(code);

        if (ticketOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticket = ticketOpt.get();

        if ("USED".equals(ticket.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Lippu on jo käytetty
        }

        
        ticket.setStatus("USED");
        ticket.setUsedAt(LocalDateTime.now());
        Ticket updatedTicket = ticketRepository.save(ticket);

        return ResponseEntity.ok(updatedTicket);
    }
}
