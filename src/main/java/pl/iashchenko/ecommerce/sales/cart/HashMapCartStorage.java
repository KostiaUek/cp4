package pl.iashchenko.ecommerce.sales.cart;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapCartStorage {
    // In-memory store of carts per customer
    private final Map<String, Cart> storage = new ConcurrentHashMap<>();

    public Optional<Cart> getForCustomer(String customerId) {
        // computeIfAbsent will insert a fresh Cart(customerId) on first call
        Cart cart = storage.computeIfAbsent(customerId, id -> new Cart(id));
        return Optional.of(cart);
    }

    public void saveCart(Cart cart) {
        storage.put(cart.getCustomerId(), cart);
    }
}