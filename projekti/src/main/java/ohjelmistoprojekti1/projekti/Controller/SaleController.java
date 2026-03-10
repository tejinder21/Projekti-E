package ohjelmistoprojekti1.projekti.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ohjelmistoprojekti1.projekti.DTO.CreateSaleRequest;
import ohjelmistoprojekti1.projekti.DTO.SaleResponse;
import ohjelmistoprojekti1.projekti.domain.AppUser;
import ohjelmistoprojekti1.projekti.domain.Sale;
import ohjelmistoprojekti1.projekti.domain.Ticket;
import ohjelmistoprojekti1.projekti.domain.TicketType;
=======
import org.springframework.web.bind.annotation.*;

import ohjelmistoprojekti1.projekti.domain.AppUser;
import ohjelmistoprojekti1.projekti.domain.Sale;
import ohjelmistoprojekti1.projekti.domain.Ticket;
import ohjelmistoprojekti1.projekti.dto.CreateSaleRequest;
import ohjelmistoprojekti1.projekti.dto.SaleResponse;
>>>>>>> DTO-testit
import ohjelmistoprojekti1.projekti.repository.AppUserRepository;
import ohjelmistoprojekti1.projekti.repository.SaleRepository;
import ohjelmistoprojekti1.projekti.repository.TicketRepository;
import ohjelmistoprojekti1.projekti.repository.TicketTypeRepository;
import ohjelmistoprojekti1.projekti.util.TicketCodeGenerator;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleRepository saleRepository;
    private final TicketRepository ticketRepository;
    private final AppUserRepository appUserRepository;
    private final TicketTypeRepository ticketTypeRepository;

<<<<<<< HEAD
    public SaleController(SaleRepository saleRepository, TicketRepository ticketRepository,
            AppUserRepository appUserRepository, TicketTypeRepository ticketTypeRepository) {
=======
    public SaleController(SaleRepository saleRepository,
                          TicketRepository ticketRepository,
                          AppUserRepository appUserRepository) {
>>>>>>> DTO-testit
        this.saleRepository = saleRepository;
        this.ticketRepository = ticketRepository;
        this.appUserRepository = appUserRepository;
        this.ticketTypeRepository = ticketTypeRepository;
    }

<<<<<<< HEAD
    // POST /api/sales - Luo uuden myyntitapahtuman ja luo automaattisesti liput
    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@RequestBody CreateSaleRequest request) {
        
        try {
            
            if (request.getSellerId() == null || request.getEventId() == null || 
                request.getItems() == null || request.getItems().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

           
            AppUser seller = appUserRepository.findById(request.getSellerId())
                    .orElseThrow(() -> new RuntimeException("Myyjä ei löytynyt"));
            Sale sale = new Sale();
            sale.setCreatedAt(LocalDateTime.now());
            sale.setSeller(seller);

            BigDecimal totalAmount = BigDecimal.ZERO;
            List<Long> createdTicketIds = new ArrayList<>();

            for (CreateSaleRequest.SaleItem item : request.getItems()) {
                TicketType ticketType = ticketTypeRepository.findById(item.getTicketTypeId())
                        .orElseThrow(() -> new RuntimeException("Lipputyyppi ei löytynyt: " + item.getTicketTypeId()));

         
                for (int i = 0; i < item.getQuantity(); i++) {
                    Ticket ticket = new Ticket();
                    ticket.setTicketType(ticketType);
                    ticket.setCode(TicketCodeGenerator.generateCode());
                    ticket.setStatus("VALID");
                    ticket.setSale(sale);

                    Ticket savedTicket = ticketRepository.save(ticket);
                    createdTicketIds.add(savedTicket.getId());

                    totalAmount = totalAmount.add(ticketType.getPrice());
                }
            }

            
            sale.setTotalAmount(totalAmount);

           
            Sale savedSale = saleRepository.save(sale);

           
            SaleResponse response = new SaleResponse();
            response.setId(savedSale.getId());
            response.setCreatedAt(savedSale.getCreatedAt());
            response.setTotalAmount(savedSale.getTotalAmount());
            response.setSellerId(seller.getId());
            response.setTicketIds(createdTicketIds);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
=======
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
>>>>>>> DTO-testit
        }
    }

<<<<<<< HEAD
    // GET /api/sales - Hae kaikki myyntitapahtumat
    @GetMapping
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // GET /api/sales/{id} - Hae yksittäinen myyntitapahtuma ID:n perusteella
    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Optional<Sale> sale = saleRepository.findById(id);
        return sale.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
=======
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
>>>>>>> DTO-testit
