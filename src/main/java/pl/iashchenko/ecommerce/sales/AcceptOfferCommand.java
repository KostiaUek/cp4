package pl.iashchenko.ecommerce.sales;

public class AcceptOfferCommand {

    private String firstName;
    private String lastName;
    private String email;

    public String getFirstName() {
        return this.firstName;
    }

    public AcceptOfferCommand setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AcceptOfferCommand setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AcceptOfferCommand setEmail(String email) {
        this.email = email;
        return this;
    }
}
