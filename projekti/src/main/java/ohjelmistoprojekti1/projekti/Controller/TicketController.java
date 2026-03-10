package ohjelmistoprojekti1.projekti.Controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ohjelmistoprojekti1.projekti.domain.Ticket;
import ohjelmistoprojekti1.projekti.domain.TicketType;
import ohjelmistoprojekti1.projekti.dto.CreateTicketRequest;
import ohjelmistoprojekti1.projekti.repository.TicketRepository;
import ohjelmistoprojekti1.projekti.repository.TicketTypeRepository;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    private final TicketTypeRepository ticketTypeRepository;

    public TicketController(TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody CreateTicketRequest req) {
        if (req.getTicketTypeId() == null
                || req.getCode() == null || req.getCode().isBlank()
                || req.getStatus() == null || req.getStatus().isBlank()) {
            return ResponseEntity.badRequest().body("ticketTypeId, code and status are required");
        }

        TicketType tt = ticketTypeRepository.findById(req.getTicketTypeId()).orElse(null);
        if (tt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TicketType not found: " + req.getTicketTypeId());
        }

        Ticket ticket = new Ticket();
        ticket.setTicketType(tt);
        ticket.setCode(req.getCode());
        ticket.setStatus(req.getStatus());
        // sale jätetään nulliksi (ei myyty vielä)
        // usedAt jätetään nulliksi

        Ticket saved = ticketRepository.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}

