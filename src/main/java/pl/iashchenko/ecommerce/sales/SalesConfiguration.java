package pl.iashchenko.ecommerce.sales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.iashchenko.ecommerce.sales.cart.HashMapCartStorage;
import pl.iashchenko.ecommerce.sales.offering.OfferCalculator;
import pl.iashchenko.ecommerce.sales.payment.PaymentDetails;
import pl.iashchenko.ecommerce.sales.payment.RegisterPaymentRequest;
import pl.iashchenko.ecommerce.sales.reservation.ReservationRepository;

@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade createMySalesFacade() {
        return new SalesFacade(new HashMapCartStorage(),
                new OfferCalculator(),
                (RegisterPaymentRequest request) -> {
                    return new PaymentDetails("http://payment");
                },
                new ReservationRepository());
    }
}
