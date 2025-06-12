package pl.iashchenko.ecommerce.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.iashchenko.ecommerce.sales.cart.HashMapCartStorage;
import pl.iashchenko.ecommerce.sales.offering.OfferCalculator;
import pl.iashchenko.ecommerce.sales.payment.PaymentDetails;
import pl.iashchenko.ecommerce.sales.payment.RegisterPaymentRequest;
import pl.iashchenko.ecommerce.sales.reservation.ReservationRepository;
import pl.iashchenko.productcatalog.ArrayListProductStorage;
import pl.iashchenko.productcatalog.ProductCatalog;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SalesTest {
    ProductCatalog catalog;

    @BeforeEach
    void setup() {
        catalog = new ProductCatalog(new ArrayListProductStorage());
    }


    @Test
    void itShowsEmptyOffer() {
        SalesFacade sales = thereIsSalesFacade();
        String customerId = thereIsCustomer("Kuba");

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.ZERO, offer.getTotal());
    }

    @Test
    void itAllowsToCollectProducts() {
        SalesFacade sales = thereIsSalesFacade();
        String customerId = thereIsCustomer("Kuba");
        String productId = thereIsProduct("Product X", BigDecimal.valueOf(10));

        sales.addToCard(customerId, productId);
        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.valueOf(10), offer.getTotal());
    }

    @Test
    void itAllowsToCollectProductsByCustomersSeparately() {
        SalesFacade sales = thereIsSalesFacade();
        String customerId1 = thereIsCustomer("Kuba");
        String customerId2 = thereIsCustomer("Kuba2");
        String productId = thereIsProduct("Product X", BigDecimal.valueOf(10));

        sales.addToCard(customerId1, productId);
        sales.addToCard(customerId2, productId);
        sales.addToCard(customerId2, productId);

        Offer offer1 = sales.getCurrentOffer(customerId1);
        Offer offer2 = sales.getCurrentOffer(customerId2);

        assertEquals(BigDecimal.valueOf(10), offer1.getTotal());
        assertEquals(BigDecimal.valueOf(20), offer2.getTotal());
    }

    private String thereIsProduct(String productName, BigDecimal bigDecimal) {
        var id = catalog.createProduct(productName, "descs");
        catalog.changePrice(id, bigDecimal);

        return id;
    }

    private String thereIsCustomer(String customerName) {
        return String.format("customer__%s", customerName);
    }

    private SalesFacade thereIsSalesFacade() {
        return new SalesFacade(new HashMapCartStorage(),
                new OfferCalculator(),
                (RegisterPaymentRequest request) -> {
                    return new PaymentDetails("http://payment");
                },
                new ReservationRepository());
    }
}
