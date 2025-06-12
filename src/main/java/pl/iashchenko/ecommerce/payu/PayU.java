package pl.iashchenko.ecommerce.payu;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PayU {
    private final RestTemplate http;
    private final PayUCredentials cfg;

    public PayU(RestTemplate http, PayUCredentials cfg) {
        this.http = http;
        this.cfg = cfg;
    }

    public OrderCreateResponse handle(OrderCreateRequest request) {
        var url = getUrl("/api/v2_1/orders");

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");
        headers.add("Authorization", String.format("Bearer %s", getToken()));

        HttpEntity<OrderCreateRequest> requestHttpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<OrderCreateResponse> response = http.postForEntity(
                url,
                requestHttpEntity,
                OrderCreateResponse.class
        );

        return response.getBody();
    }

    private String getToken() {
        var url = getUrl("/pl/standard/user/oauth/authorize");
        String body = String.format("grant_type=client_credentials&client_id=%s&client_secret=%s",
                cfg.getClientId(),
                cfg.getClientSecret()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<AuthorizationResponse> response = http.postForEntity(
                url, request, AuthorizationResponse.class);

        return response.getBody().getAccessToken();
    }

    private String getUrl(String path) {
        return String.format("%s%s", cfg.getBaseUrl(), path);
    }
}
