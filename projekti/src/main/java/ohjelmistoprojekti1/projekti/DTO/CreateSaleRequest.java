package ohjelmistoprojekti1.projekti.DTO;

import java.util.List;

public class CreateSaleRequest {

    private Long sellerId;      
    private Long eventId;       
    private List<SaleItem> items; 

    public static class SaleItem {
        private Long ticketTypeId;
        private int quantity;

        public Long getTicketTypeId() {
            return ticketTypeId;
        }

        public void setTicketTypeId(Long ticketTypeId) {
            this.ticketTypeId = ticketTypeId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }
}
