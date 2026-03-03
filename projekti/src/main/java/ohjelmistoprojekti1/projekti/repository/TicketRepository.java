package ohjelmistoprojekti1.projekti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ohjelmistoprojekti1.projekti.domain.Ticket;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByCode(String code);
    List<Ticket> findByStatus(String status);
}
