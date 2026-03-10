package ohjelmistoprojekti1.projekti.dto;

import java.math.BigDecimal;

public class CreateTicketTypeRequest {
    private String description;
    private BigDecimal price;
    private Long eventId;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
}