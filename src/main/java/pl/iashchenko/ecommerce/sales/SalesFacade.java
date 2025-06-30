package pl.iashchenko.ecommerce.sales;

import pl.iashchenko.ecommerce.sales.cart.Cart;
import pl.iashchenko.ecommerce.sales.cart.HashMapCartStorage;
import pl.iashchenko.ecommerce.sales.offering.OfferCalculator;
import pl.iashchenko.ecommerce.sales.payment.PaymentDetails;
import pl.iashchenko.ecommerce.sales.payment.PaymentGateway;
import pl.iashchenko.ecommerce.sales.payment.RegisterPaymentRequest;
import pl.iashchenko.ecommerce.sales.reservation.Reservation;
import pl.iashchenko.ecommerce.sales.reservation.ReservationRepository;

import java.util.UUID;

public class SalesFacade {

    private HashMapCartStorage cartStorage;
    private OfferCalculator offerCalculator;
    private PaymentGateway paymentGateway;
    private ReservationRepository reservationRepository;


    public SalesFacade(HashMapCartStorage cartStorage, OfferCalculator offerCalculator, PaymentGateway paymentGateway, ReservationRepository reservationRespository) {
        this.cartStorage = cartStorage;
        this.offerCalculator = offerCalculator;
        this.paymentGateway = paymentGateway;
        this.reservationRepository = reservationRespository;
    }

    public void addProduct(String customerId, String productId) {
        Cart cart = getCartForCustomer(customerId);

        cart.add(productId);
    }

    private Cart getCartForCustomer(String customerId) {
        return cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());
    }

    // TODO merge this into addProduct
    public void addToCard(String customerId, String productId) {
        Cart cart = getCartForCustomer(customerId);

        cart.add(productId);
    }

    public ReservationDetails acceptOffer(String customerId, AcceptOfferCommand acceptOfferRequest) {
        String reservationId = UUID.randomUUID().toString();
        Offer offer = this.getCurrentOffer(customerId);

        PaymentDetails paymentDetails = paymentGateway.registerPayment(
                RegisterPaymentRequest.of(reservationId, acceptOfferRequest, offer.getTotal())
        );

        Reservation reservation = Reservation.of(
                reservationId,
                customerId,
                acceptOfferRequest,
                offer,
                paymentDetails);

        reservationRepository.add(reservation);
        
        // clear customers cart (imitate the procedure)
        Cart currentCart = getCurrentCart(customerId);
        currentCart.clear();

        return new ReservationDetails(reservationId, paymentDetails.getPaymentUrl());
    }

    public Offer getCurrentOffer(String customerId) {
        return new Offer();
    }

    public Cart getCurrentCart(String customerId) {
        return getCartForCustomer(customerId);
    }

    public void makeReservationPaid(String reservationId) {
    }


}
