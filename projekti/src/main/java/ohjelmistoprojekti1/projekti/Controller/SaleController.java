package ohjelmistoprojekti1.projekti.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ohjelmistoprojekti1.projekti.DTO.SaleResponse;
import ohjelmistoprojekti1.projekti.domain.AppUser;
import ohjelmistoprojekti1.projekti.domain.Sale;
import ohjelmistoprojekti1.projekti.domain.Ticket;
import ohjelmistoprojekti1.projekti.repository.AppUserRepository;
import ohjelmistoprojekti1.projekti.repository.SaleRepository;
import ohjelmistoprojekti1.projekti.repository.TicketRepository;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleRepository saleRepository;
    private final TicketRepository ticketRepository;
    private final AppUserRepository appUserRepository;

    public SaleController(SaleRepository saleRepository, TicketRepository ticketRepository,
            AppUserRepository appUserRepository) {
        this.saleRepository = saleRepository;
        this.ticketRepository = ticketRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleResponse saleResponse) {

        // Fetch seller
        AppUser seller = appUserRepository.findById(saleResponse.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // Fetch tickets
        List<Ticket> tickets = ticketRepository.findAllById(saleResponse.getTicketIds());

        if (tickets.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Create Sale
        Sale sale = new Sale();
        sale.setCreatedAt(LocalDateTime.now());
        sale.setSeller(seller);

        // Set total amount (or leave null)
        sale.setTotalAmount(saleResponse.getTotalAmount());

        // Save Sale
        Sale savedSale = saleRepository.save(sale);

        // Attach tickets to sale
        for (Ticket ticket : tickets) {
            ticket.setSale(savedSale);
            ticketRepository.save(ticket);
        }

        // Prepare response
        SaleResponse responseDTO = new SaleResponse();
        responseDTO.setId(savedSale.getId());
        responseDTO.setCreatedAt(savedSale.getCreatedAt());
        responseDTO.setTotalAmount(savedSale.getTotalAmount());
        responseDTO.setSellerId(seller.getId());
        responseDTO.setTicketIds(saleResponse.getTicketIds());

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }

}
