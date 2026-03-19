package ohjelmistoprojekti1.projekti.Controller;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import ohjelmistoprojekti1.projekti.domain.Event;
import ohjelmistoprojekti1.projekti.domain.TicketType;
import ohjelmistoprojekti1.projekti.dto.EventResponse;
import ohjelmistoprojekti1.projekti.repository.EventRepository;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Muunna Event -> EventResponse
    private EventResponse toResponse(Event event) {
        EventResponse dto = new EventResponse();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setVenue(event.getVenue());
        dto.setCity(event.getCity());
        dto.setStartTime(event.getStartTime());

        if (event.getTicketTypes() != null) {
            List<EventResponse.TicketTypeMini> ticketTypeDtos = event.getTicketTypes().stream()
                    .map(this::toTicketTypeMini)
                    .toList();
            dto.setTicketTypes(ticketTypeDtos);
        }

        return dto;
    }

    // Muunna TicketType -> TicketTypeMini
    private EventResponse.TicketTypeMini toTicketTypeMini(TicketType ticketType) {
        EventResponse.TicketTypeMini mini = new EventResponse.TicketTypeMini();
        mini.setId(ticketType.getId());
        mini.setDescription(ticketType.getDescription());
        mini.setPrice(ticketType.getPrice());
        return mini;
    }

    // GET http://localhost:8080/api/events
    @GetMapping
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // GET http://localhost:8080/api/events/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return ResponseEntity.ok(toResponse(event.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST http://localhost:8080/api/events
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody Event event) {
        Event savedEvent = eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(savedEvent));
    }

    // PUT http://localhost:8080/api/events/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long id, @Valid @RequestBody Event eventDetails) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setName(eventDetails.getName());
            event.setVenue(eventDetails.getVenue());
            event.setCity(eventDetails.getCity());
            event.setStartTime(eventDetails.getStartTime());

            Event updatedEvent = eventRepository.save(event);
            return ResponseEntity.ok(toResponse(updatedEvent));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE http://localhost:8080/api/events/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET http://localhost:8080/api/events/search?name={name}&city={city}
    @GetMapping("/search")
    public List<EventResponse> searchEvents(@RequestParam(required = false) String name,
            @RequestParam(required = false) String city) {

        List<Event> events;

        if (name != null && !name.isEmpty()) {
            events = eventRepository.findByNameContainingIgnoreCase(name);
        } else if (city != null && !city.isEmpty()) {
            events = eventRepository.findByCityContainingIgnoreCase(city);
        } else {
            events = eventRepository.findAll();
        }

        return events.stream()
                .map(this::toResponse)
                .toList();
    }
}