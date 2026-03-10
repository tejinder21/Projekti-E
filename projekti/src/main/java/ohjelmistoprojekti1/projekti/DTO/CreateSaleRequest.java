package ohjelmistoprojekti1.projekti.dto;

import java.util.List;

import java.util.List;

public class CreateSaleRequest {

<<<<<<< HEAD
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
=======
    private Long sellerId;
    private List<Long> ticketIds;
>>>>>>> DTO-testit

    public Long getSellerId() {
        return sellerId;
    }
<<<<<<< HEAD

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
=======
    public void setSellerId(Long sellerId) {this.sellerId = sellerId;}

    public List<Long> getTicketIds() { return ticketIds; }
    public void setTicketIds(List<Long> ticketIds) { this.ticketIds = ticketIds; }

>>>>>>> DTO-testit
}



// Mikä on DTO? 

// DTO on “API:n muoto”, Entity on “tietokannan muoto”.
// Kun client lähettää JSONin, et halua pakottaa sitä näyttämään täsmälleen entityltä. DTO tekee siitä selkeän.