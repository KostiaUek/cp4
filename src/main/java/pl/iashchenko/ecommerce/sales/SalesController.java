package pl.iashchenko.ecommerce.sales;

import org.springframework.web.bind.annotation.*;

@RestController
public class SalesController {
    SalesFacade salesFacade;

    public SalesController(SalesFacade salesFacade) {
        this.salesFacade = salesFacade;
    }

    @GetMapping("/api/current-offer")
    Offer getCurrentOffer() {
        return salesFacade.getCurrentOffer(getCurrentCustomer());
    }

    @PostMapping("/api/add-product/{productId}")
    void addProduct(@PathVariable(name = "productId") String productId) {
        salesFacade.addToCard(getCurrentCustomer(), productId);
    }

    @PostMapping("/api/accept-offer")
    ReservationDetails acceptOffer(@RequestBody AcceptOfferCommand acceptOfferRequest) {
        var customerId = getCurrentCustomer();
        return salesFacade.acceptOffer(customerId, acceptOfferRequest);
    }

    private String getCurrentCustomer() {
        return "kuba";
    }
}
