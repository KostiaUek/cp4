package pl.iashchenko.ecommerce.sales;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import pl.iashchenko.productcatalog.Product;
import pl.iashchenko.productcatalog.ProductCatalog;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalesHttpTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate http;

    @Autowired
    ProductCatalog catalog;
    @Test
    void emptyOffer(){
        var toBeCalledURLOffer = getBaseURL("/api/current-offer");
        ResponseEntity<Offer> offerHttp = http.getForEntity(toBeCalledURLOffer, Offer.class);

        assertEquals(BigDecimal.valueOf(0), offerHttp.getBody().getTotal());
    }


    @Test
    void checkoutHappyPath(){
        String productId = thereIsProduct("1234", BigDecimal.valueOf(11));

        var toBeCalledUrl = getBaseURL(String.format("/api/add-product/%s", productId));

        http.postForEntity(toBeCalledUrl, null, null);
        http.postForEntity(toBeCalledUrl, null, null);

        var toBeCalledURLOffer = getBaseURL("/api/current-offer");
        ResponseEntity<Offer> offerHttp = http.getForEntity(toBeCalledURLOffer, Offer.class);

        assertEquals(BigDecimal.valueOf(22), offerHttp.getBody().getTotal());
    }

    private String thereIsProduct(String name, BigDecimal bigDecimal) {
        var id = catalog.createProduct(name, "afeea");
        catalog.changePrice(id, bigDecimal);

        return id;
    }

    private String getBaseURL(String endpoint) {
        return String.format("http://localhost:%s%s", port, endpoint);
    }
}
