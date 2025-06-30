package pl.iashchenko.productcatalog;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ProductCatalog {

    ProductStorage productStorage;

    public ProductCatalog(ProductStorage productStorage) {
        this.productStorage = productStorage;
    } // TECH

    public List<Product> allProducts() {
        Product mug = new Product(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Coffee Mug",
                "A sturdy mug for your morning coffee."
        );
        mug.changePrice(BigDecimal.valueOf(7.50));
        mug.setImage("https://via.placeholder.com/200x140?text=Coffee+Mug");

        Product notebook = new Product(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Notebook",
                "A ruled notebook for notes and sketches."
        );
        notebook.changePrice(BigDecimal.valueOf(3.25));
        notebook.setImage("https://via.placeholder.com/200x140?text=Notebook");

        Product bottle = new Product(
                UUID.fromString("33333333-3333-3333-3333-333333333333"),
                "Water Bottle",
                "Keeps your drinks cold for hours."
        );
        bottle.changePrice(BigDecimal.valueOf(12.00));
        bottle.setImage("https://via.placeholder.com/200x140?text=Water+Bottle");

        return Arrays.asList(mug, notebook, bottle);
//        return productStorage.allProducts();
    } //TECH

    public String createProduct(String name, String description) {
        var uuid = UUID.randomUUID();

        var newProduct = new Product(
                uuid,
                name,
                description
        ); // domain

        this.productStorage.save(newProduct); // tech

        return newProduct.getId();
    }

    public Product loadProductById(String productId) {
        return productStorage.loadProductById(productId);
    }

    public void changePrice(String productId, BigDecimal bigDecimal) {
        var product = this.loadProductById(productId);

        if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) { // DOMAIN (business)
            throw new InvalidPriceException();
        }

        product.changePrice(bigDecimal);
    }

    public void changeImage(String productId, String url) {
        var product = this.loadProductById(productId);

        product.setImage(url); // DOMAIN
    }
}
