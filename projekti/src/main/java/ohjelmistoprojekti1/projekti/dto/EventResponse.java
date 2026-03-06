package ohjelmistoprojekti1.projekti.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

public class EventResponse {
    private Long id;
    private String name;
    private String venue;
    private String city;
    private LocalDateTime startTime;
    private List<TicketTypeMini> ticketTypes;

    public static class TicketTypeMini {
        private Long id;
        private String description;
        private BigDecimal price;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public List<TicketTypeMini> getTicketTypes() { return ticketTypes; }
    public void setTicketTypes(List<TicketTypeMini> ticketTypes) { this.ticketTypes = ticketTypes; }
}