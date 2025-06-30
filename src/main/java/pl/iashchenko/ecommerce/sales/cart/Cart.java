package pl.iashchenko.ecommerce.sales.cart;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private final String customerId;
    private final HashMap<String, Integer> productsQuantities;

    public Cart() {
        this.customerId = null;
        this.productsQuantities = new HashMap<>();
    }

    public Cart(String customerId) {
        this.customerId = customerId;
        this.productsQuantities = new HashMap<>();
    }

    public static Cart empty() {
        return new Cart();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void add(String product) {
        if (!productsQuantities.containsKey(product)) {
            productsQuantities.put(product, 1);
        } else {
            productsQuantities.put(product, productsQuantities.get(product) + 1);
        }
    }

    public boolean isEmpty() {
        return productsQuantities.isEmpty();
    }

    public int getItemsCount() {
        return productsQuantities.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public List<CartItem> getCartItems() {
        return productsQuantities.entrySet().stream()
                .map(es -> new CartItem(es.getKey(), es.getValue()))
                .collect(Collectors.toList());
    }

    public void clear() {
        productsQuantities.clear();
    }
}
