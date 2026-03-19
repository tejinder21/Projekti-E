package ohjelmistoprojekti1.projekti.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // sarakkeet: name, venue, city, start_time

    @NotBlank(message = "Tapahtuman nimi ei voi olla tyhjä")
    @Size(min = 2, max = 100, message = "Nimen täytyy olla 2-100 merkkiä pitkä")
    private String name;

    @NotBlank(message = "Tapahtumapaikka vaaditaan")
    @Size(max = 100)
    private String venue;

    @NotBlank(message = "Kaupunki vaaditaan")
    @Size(max = 50)
    private String city;

    @NotNull(message = "Aloitusaika vaaditaan")
    @FutureOrPresent(message = "Aloitusaika ei voi olla menneisyydessä")
    private LocalDateTime startTime;

    @OneToMany(mappedBy = "event")

    // suhde TicketType-tauluun
    // 1 Event -> monta TicketTypea (*)
    private List<TicketType> ticketTypes;

    // Getters and setters
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public List<TicketType> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<TicketType> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }
}