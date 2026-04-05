package ohjelmistoprojekti1.projekti.dto;

import java.time.LocalDateTime;

public class UseTicketRequest {

    private LocalDateTime used;

    public LocalDateTime getUsed() {
        return used;
    }

    public void setUsed(LocalDateTime used) {
        this.used = used;
    }
}