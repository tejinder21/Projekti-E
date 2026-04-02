package ohjelmistoprojekti1.projekti.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ohjelmistoprojekti1.projekti.dto.CreateSaleRequest;
import ohjelmistoprojekti1.projekti.dto.SaleResponse;
import ohjelmistoprojekti1.projekti.domain.AppUser;
import ohjelmistoprojekti1.projekti.domain.Event;
import ohjelmistoprojekti1.projekti.domain.Sale;
import ohjelmistoprojekti1.projekti.domain.Ticket;
import ohjelmistoprojekti1.projekti.domain.TicketType;
import ohjelmistoprojekti1.projekti.repository.AppUserRepository;
import ohjelmistoprojekti1.projekti.repository.EventRepository;
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
    private final EventRepository eventRepository;

    public SaleController(SaleRepository saleRepository,
                          TicketRepository ticketRepository,
                          AppUserRepository appUserRepository,
                          TicketTypeRepository ticketTypeRepository,
                          EventRepository eventRepository) {
        this.saleRepository = saleRepository;
        this.ticketRepository = ticketRepository;
        this.appUserRepository = appUserRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@RequestBody CreateSaleRequest request) {
        try {
            if (request.getSellerId() == null ||
                request.getEventId() == null ||
                request.getItems() == null ||
                request.getItems().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            AppUser seller = appUserRepository.findById(request.getSellerId())
                    .orElseThrow(() -> new RuntimeException("Myyjä ei löytynyt"));

            Event event = eventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new RuntimeException("Tapahtumaa ei löytynyt"));

            Sale sale = new Sale();
            sale.setCreatedAt(LocalDateTime.now());
            sale.setSeller(seller);
            sale.setEvent(event);
            sale.setTotalAmount(BigDecimal.ZERO);

            Sale savedSale = saleRepository.save(sale);

            BigDecimal totalAmount = BigDecimal.ZERO;

            for (CreateSaleRequest.SaleItem item : request.getItems()) {
                if (item.getTicketTypeId() == null || item.getQuantity() <= 0) {
                    return ResponseEntity.badRequest().build();
                }

                TicketType ticketType = ticketTypeRepository.findById(item.getTicketTypeId())
                        .orElseThrow(() -> new RuntimeException(
                                "Lipputyyppi ei löytynyt: " + item.getTicketTypeId()));

                for (int i = 0; i < item.getQuantity(); i++) {
                    Ticket ticket = new Ticket();
                    ticket.setTicketType(ticketType);
                    ticket.setCode(TicketCodeGenerator.generateCode());
                    ticket.setStatus("VALID");
                    ticket.setSale(savedSale);

                    ticketRepository.save(ticket);
                    totalAmount = totalAmount.add(ticketType.getPrice());
                }
            }

            savedSale.setTotalAmount(totalAmount);
            Sale updatedSale = saleRepository.save(savedSale);

            return ResponseEntity.status(HttpStatus.CREATED).body(toSaleResponse(updatedSale));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<SaleResponse> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        List<SaleResponse> responses = new ArrayList<>();

        for (Sale sale : sales) {
            responses.add(toSaleResponse(sale));
        }

        return responses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id) {
        Optional<Sale> sale = saleRepository.findById(id);

        if (sale.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toSaleResponse(sale.get()));
    }

    private SaleResponse toSaleResponse(Sale sale) {
        SaleResponse response = new SaleResponse();
        response.setId(sale.getId());
        response.setCreatedAt(sale.getCreatedAt());
        response.setTotalAmount(sale.getTotalAmount());

        if (sale.getSeller() != null) {
            SaleResponse.SellerInfo sellerInfo = new SaleResponse.SellerInfo();
            sellerInfo.setId(sale.getSeller().getId());
            sellerInfo.setUsername(sale.getSeller().getUsername());
            response.setSeller(sellerInfo);
        }

        if (sale.getEvent() != null) {
            SaleResponse.EventInfo eventInfo = new SaleResponse.EventInfo();
            eventInfo.setId(sale.getEvent().getId());
            eventInfo.setName(sale.getEvent().getName());
            response.setEvent(eventInfo);
        }

        List<SaleResponse.TicketInfo> ticketInfos = new ArrayList<>();
        Map<Long, SaleResponse.SaleItemInfo> groupedItems = new LinkedHashMap<>();

        if (sale.getTickets() != null) {
            for (Ticket ticket : sale.getTickets()) {
                SaleResponse.TicketInfo ticketInfo = new SaleResponse.TicketInfo();
                ticketInfo.setId(ticket.getId());
                ticketInfo.setCode(ticket.getCode());
                ticketInfo.setStatus(ticket.getStatus());
                ticketInfo.setUsedAt(ticket.getUsedAt());
                ticketInfos.add(ticketInfo);

                if (ticket.getTicketType() != null) {
                    Long ticketTypeId = ticket.getTicketType().getId();

                    if (!groupedItems.containsKey(ticketTypeId)) {
                        SaleResponse.SaleItemInfo itemInfo = new SaleResponse.SaleItemInfo();
                        itemInfo.setTicketTypeId(ticketTypeId);
                        itemInfo.setTicketTypeDescription(ticket.getTicketType().getDescription());
                        itemInfo.setUnitPrice(ticket.getTicketType().getPrice());
                        itemInfo.setQuantity(0);
                        itemInfo.setLineTotal(BigDecimal.ZERO);
                        groupedItems.put(ticketTypeId, itemInfo);
                    }

                    SaleResponse.SaleItemInfo existing = groupedItems.get(ticketTypeId);
                    existing.setQuantity(existing.getQuantity() + 1);
                    existing.setLineTotal(
                            existing.getUnitPrice().multiply(BigDecimal.valueOf(existing.getQuantity()))
                    );
                }
            }
        }

        response.setTickets(ticketInfos);
        response.setItems(new ArrayList<>(groupedItems.values()));

        return response;
    }
}