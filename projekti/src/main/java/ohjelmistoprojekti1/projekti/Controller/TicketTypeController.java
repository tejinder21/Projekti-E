package ohjelmistoprojekti1.projekti.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ohjelmistoprojekti1.projekti.domain.Event;
import ohjelmistoprojekti1.projekti.domain.TicketType;
import ohjelmistoprojekti1.projekti.dto.CreateTicketTypeRequest;
import ohjelmistoprojekti1.projekti.repository.EventRepository;
import ohjelmistoprojekti1.projekti.repository.TicketTypeRepository;

@RestController
@RequestMapping("/api/tickettypes")
public class TicketTypeController {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;

    public TicketTypeController(TicketTypeRepository ticketTypeRepository, EventRepository eventRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<TicketType> getAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createTicketType(@RequestBody CreateTicketTypeRequest req) {
        if (req.getDescription() == null || req.getDescription().isBlank()
                || req.getPrice() == null
                || req.getEventId() == null) {
            return ResponseEntity.badRequest().body("description, price and eventId are required");
        }

        Event event = eventRepository.findById(req.getEventId()).orElse(null);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found: " + req.getEventId());
        }

        TicketType tt = new TicketType();
        tt.setDescription(req.getDescription());
        tt.setPrice(req.getPrice());
        tt.setEvent(event);

        TicketType saved = ticketTypeRepository.save(tt);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}