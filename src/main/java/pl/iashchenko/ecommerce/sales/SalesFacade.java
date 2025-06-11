package pl.iashchenko.ecommerce.sales;

public class SalesFacade {

    public void addProduct(String customerId, String productId) {

    }

    public void addToCard(String customerId, String productId) {

    }

    public void acceptOffer(AcceptOfferCommand acceptOfferCommand) {
    }

    public Offer getCurrentOffer(String customerId) {
        return new Offer();
    }

    public void makeReservationPaid(String reservationId) {
    }

    public ReservationDetails acceptOffer(String customerId, AcceptOfferCommand acceptOfferRequest) {
        return new ReservationDetails();
    }


}
