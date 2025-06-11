package pl.iashchenko.ecommerce.sales.cart;

public class CartItem {
    private final String productId;
    private final Integer qty;

    public CartItem(String productId, Integer qty) {
        this.productId = productId;
        this.qty = qty;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return qty;
    }
}
