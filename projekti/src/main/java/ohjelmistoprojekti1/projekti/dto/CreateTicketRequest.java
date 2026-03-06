package ohjelmistoprojekti1.projekti.dto;

public class CreateTicketRequest {
    private Long ticketTypeId;
    private String code;
    private String status; // VALID / USED

    public Long getTicketTypeId() { return ticketTypeId; }
    public void setTicketTypeId(Long ticketTypeId) { this.ticketTypeId = ticketTypeId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}