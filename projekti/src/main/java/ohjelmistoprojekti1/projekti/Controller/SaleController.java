package ohjelmistoprojekti1.projekti.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ohjelmistoprojekti1.projekti.domain.AppUser;
import ohjelmistoprojekti1.projekti.domain.Sale;
import ohjelmistoprojekti1.projekti.domain.Ticket;
import ohjelmistoprojekti1.projekti.dto.CreateSaleRequest;
import ohjelmistoprojekti1.projekti.dto.SaleResponse;
import ohjelmistoprojekti1.projekti.repository.AppUserRepository;
import ohjelmistoprojekti1.projekti.repository.SaleRepository;
import ohjelmistoprojekti1.projekti.repository.TicketRepository;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleRepository saleRepository;
    private final TicketRepository ticketRepository;
    private final AppUserRepository appUserRepository;

    public SaleController(SaleRepository saleRepository,
                          TicketRepository ticketRepository,
                          AppUserRepository appUserRepository) {
        this.saleRepository = saleRepository;
        this.ticketRepository = ticketRepository;
        this.appUserRepository = appUserRepository;
    }

    // POST http://localhost:8080/api/sales
    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody CreateSaleRequest req) {

        // 1) Validointi
        if (req.getSellerId() == null || req.getTicketIds() == null || req.getTicketIds().isEmpty()) {
            return ResponseEntity.badRequest().body("sellerId and ticketIds are required");
        }

        // 2) Hae myyjä (AppUser)
        AppUser seller = appUserRepository.findById(req.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found: " + req.getSellerId()));

        // (suositus) varmista rooli
        if (!"LIPUNMYYJÄ".equalsIgnoreCase(seller.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not a seller");
        }

        // 3) Hae liput
        List<Ticket> tickets = ticketRepository.findAllById(req.getTicketIds());

        // Jos joku ID ei löytynyt
        if (tickets.size() != req.getTicketIds().size()) {
            return ResponseEntity.badRequest().body("One or more tickets not found");
        }

        // Estä jo myydyt liput
        boolean anyAlreadySold = tickets.stream().anyMatch(t -> t.getSale() != null);
        if (anyAlreadySold) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("One or more tickets already sold");
        }

        // 4) Laske summa TicketType.price:sta
        BigDecimal total = tickets.stream()
                .map(t -> t.getTicketType().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 5) Luo myynti
        Sale sale = new Sale();
        sale.setCreatedAt(LocalDateTime.now());
        sale.setSeller(seller);
        sale.setTotalAmount(total);

        Sale savedSale = saleRepository.save(sale);

        // 6) Liitä liput myyntiin
        for (Ticket ticket : tickets) {
            ticket.setSale(savedSale);
        }
        ticketRepository.saveAll(tickets);

        // 7) Palauta vastaus DTO:na
        SaleResponse res = new SaleResponse();
        res.setId(savedSale.getId());
        res.setCreatedAt(savedSale.getCreatedAt());
        res.setTotalAmount(savedSale.getTotalAmount());
        res.setSellerId(seller.getId());
        res.setTicketIds(req.getTicketIds());

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}