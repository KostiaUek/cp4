package pl.iashchenko.productcatalog;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SqlProductStorageTest {

    @Test
    void itSaveAndLoadProduct() {
        var product = thereIsProduct();
        var storage = thereIsStorage();

        storage.save(product);

        var loaded = storage.loadProductById(product.getId());

        assertEquals(product.getId(), loaded.getId());
        assertEquals(product.getDescription(), loaded.getDescription());
    }

    private ProductStorage thereIsStorage() {
        return new ArrayListProductStorage();
    }

    private Product thereIsProduct() {
        return null;
    }


    @Test
    void itLoadsAllProducts() {
        Product product = thereIsProduct();
        ProductStorage storage = thereIsStorage();

        storage.save(product);

        List<Product> all = storage.allProducts();

        assertEquals(1, all.size());
    }

}
