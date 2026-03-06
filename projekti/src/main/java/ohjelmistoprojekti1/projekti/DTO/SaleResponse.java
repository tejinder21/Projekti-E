package ohjelmistoprojekti1.projekti.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SaleResponse {

    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private Long sellerId;
    private List<Long> ticketIds;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

    public List<Long> getTicketIds() { return ticketIds; }
    public void setTicketIds(List<Long> ticketIds) { this.ticketIds = ticketIds; }

}
