package ohjelmistoprojekti1.projekti.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity 

public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // FK: ticket_type_id -> TicketType.id
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    @JsonIgnore
    // suhde TicketType-tauluun
    // monta Ticketiä voi kuulua yhteen TicketTypeen
    // FK tässä taulussa: ticket_type_id
    private TicketType ticketType;

    // FK: sale_id -> Sale.id ( ovilippu voi olla ilman myyntiä)

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = true)
    // suhde Sale-tauluun
    // yksi myynti voi sisältää monta lippua
    // mutta lippu voi olla myös ilman myyntiä (esim. ovilippu)
    // FK tässä taulussa: sale_id
    private Sale sale; 

    @Column(unique = true)
    private String code;

    private String status; // VALID / USED

    private LocalDateTime usedAt; // aika, jolloin lippu on käytetty

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
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
