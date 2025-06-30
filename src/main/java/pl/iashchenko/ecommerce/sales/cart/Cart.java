package pl.iashchenko.ecommerce.sales.cart;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private final String customerId;
    private final HashMap<String, Integer> productsQuantities;

    /**
     * For internal “empty” use only; customerId will be null
     */
    public Cart() {
        this.customerId = null;
        this.productsQuantities = new HashMap<>();
    }

    /**
     * Main constructor: associate this cart with a customerId
     */
    public Cart(String customerId) {
        this.customerId = customerId;
        this.productsQuantities = new HashMap<>();
    }

    /**
     * Factory for an unassociated empty cart (if you really need it)
     */
    public static Cart empty() {
        return new Cart();
    }

    /**
     * ID getter needed by HashMapCartStorage.saveCart(...)
     */
    public String getCustomerId() {
        return customerId;
    }

    public void add(String product) {
        if (!isInCart(product)) {
            putIntoCart(product);
        } else {
            increaseProductQuantity(product);
        }
    }

    /**
     * True if there are no products at all in the cart
     */
    public boolean isEmpty() {
        return productsQuantities.isEmpty();
    }

    /**
     * Total item count (sum of all quantities)
     */
    public int getItemsCount() {
        return productsQuantities.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public List<CartItem> getCartItems() {
        return productsQuantities.entrySet().stream()
                .map(es -> new CartItem(es.getKey(), es.getValue()))
                .collect(Collectors.toList());
    }

    private void putIntoCart(String product) {
        productsQuantities.put(product, 1);
    }

    private void increaseProductQuantity(String product) {
        productsQuantities.put(product, productsQuantities.get(product) + 1);
    }

    private boolean isInCart(String product) {
        return productsQuantities.containsKey(product);
    }
}