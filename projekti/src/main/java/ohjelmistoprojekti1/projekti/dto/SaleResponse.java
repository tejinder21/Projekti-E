package ohjelmistoprojekti1.projekti.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SaleResponse {

    private Long id;
    private LocalDateTime createdAt;
    private SellerInfo seller;
    private EventInfo event;
    private List<SaleItemInfo> items;
    private List<TicketInfo> tickets;
    private BigDecimal totalAmount;

    public static class SellerInfo {
        private Long id;
        private String username;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class EventInfo {
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SaleItemInfo {
        private Long ticketTypeId;
        private String ticketTypeDescription;
        private BigDecimal unitPrice;
        private int quantity;
        private BigDecimal lineTotal;

        public Long getTicketTypeId() {
            return ticketTypeId;
        }

        public void setTicketTypeId(Long ticketTypeId) {
            this.ticketTypeId = ticketTypeId;
        }

        public String getTicketTypeDescription() {
            return ticketTypeDescription;
        }

        public void setTicketTypeDescription(String ticketTypeDescription) {
            this.ticketTypeDescription = ticketTypeDescription;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getLineTotal() {
            return lineTotal;
        }

        public void setLineTotal(BigDecimal lineTotal) {
            this.lineTotal = lineTotal;
        }
    }

    public static class TicketInfo {
        private Long id;
        private String code;
        private String status;
        private LocalDateTime usedAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getUsedAt() {
            return usedAt;
        }

        public void setUsedAt(LocalDateTime usedAt) {
            this.usedAt = usedAt;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public SellerInfo getSeller() {
        return seller;
    }

    public void setSeller(SellerInfo seller) {
        this.seller = seller;
    }

    public EventInfo getEvent() {
        return event;
    }

    public void setEvent(EventInfo event) {
        this.event = event;
    }

    public List<SaleItemInfo> getItems() {
        return items;
    }

    public void setItems(List<SaleItemInfo> items) {
        this.items = items;
    }

    public List<TicketInfo> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketInfo> tickets) {
        this.tickets = tickets;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}