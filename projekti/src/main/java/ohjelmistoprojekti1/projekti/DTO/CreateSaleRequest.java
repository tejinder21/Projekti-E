package ohjelmistoprojekti1.projekti.dto;

import java.util.List;

public class CreateSaleRequest {

    private Long sellerId;
    private List<Long> ticketIds;

    public Long getSellerId() {
        return sellerId;
    }
    public void setSellerId(Long sellerId) {this.sellerId = sellerId;}

    public List<Long> getTicketIds() { return ticketIds; }
    public void setTicketIds(List<Long> ticketIds) { this.ticketIds = ticketIds; }

}



// Mikä on DTO? 

// DTO on “API:n muoto”, Entity on “tietokannan muoto”.
// Kun client lähettää JSONin, et halua pakottaa sitä näyttämään täsmälleen entityltä. DTO tekee siitä selkeän.