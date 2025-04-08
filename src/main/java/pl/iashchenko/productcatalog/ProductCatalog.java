package pl.iashchenko.productcatalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ProductCatalog {

    private List<Product> products;

    public ProductCatalog() {
        this.products = new ArrayList<>();
    }

    public List<Product> allProducts() {
        return Collections.unmodifiableList(products);
    }

    public String createProduct(String name, String description) {
        var uuid = UUID.randomUUID();

        var newProduct = new Product(
                uuid,
                name,
                description
        );

        this.products.add(newProduct);

        return newProduct.getId();
    }

    public Product loadProductById(String productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .get();
    }

    public void changePrice(String productId, BigDecimal bigDecimal) {
        var product = this.loadProductById(productId);

        if(bigDecimal.compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidPriceException();
        }

        product.changePrice(bigDecimal);
    }

    public void changeImage(String productId, String url) {
        var product = this.loadProductById(productId);

        product.setImage(url);
    }
}
