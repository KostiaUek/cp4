package pl.iashchenko.ecommerce.pau;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class PayU {
    private final RestTemplate http;
    private final PayUConfiguration cfg;

    public PayU(RestTemplate http, PayUConfiguration cfg) {
        this.http = http;
        this.cfg = cfg;
    }

    public OrderCreateResponse handle(OrderCreateRequest request) {
        request.setMerchantPosId(cfg.posId);

        var url = getUrl("/api/v2_1/orders");

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");
        headers.add("Authorization", String.format("Bearer %s", getToken()));

        HttpEntity<OrderCreateRequest> requestHttpEntity = new HttpEntity<>(request, headers);

        http.postForEntity(
                url,
                requestHttpEntity,
                OrderCreateResponse.class
        );

        return null;
    }

    private String getToken() {
        return null;
    }

    private String getUrl(String path) {
        if (cfg.sandboxMode) {
            return String.format("https://secure.snd.payu.com%s", path);
        }

        return String.format("https://secure.payu.com%s", path);
    }
}
