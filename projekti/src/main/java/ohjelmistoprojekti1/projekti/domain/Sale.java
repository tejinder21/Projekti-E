package ohjelmistoprojekti1.projekti.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // tietokantataulu SALE (myyntitapahtuma)
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // myynnin ajankohta
    private LocalDateTime createdAt;

    private BigDecimal totalAmount;

    // FK: seller_id -> AppUser.id
    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnore
    // suhde käyttäjään (myyjään)
    // monta Salea voi kuulua yhdelle AppUserille
    // FK on tässä taulussa: seller_id

    private AppUser seller;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    // suhde tapahtumaan
    // monta Salea voi kuulua yhteen Eventtiin
    // FK on tässä taulussa: event_id
    private Event event;

    // Yksi myyntitapahtuma sisältää monta lippua.
    @OneToMany(mappedBy = "sale")
    // 1 Sale -> monta Ticketiä (*)
    // FK on Ticket-taulussa (sale_id)
    private List<Ticket> tickets;

    // getters & setters
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public AppUser getSeller() {
        return seller;
    }

    public void setSeller(AppUser seller) {
        this.seller = seller;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

   
}
