package pl.iashchenko.ecommerce;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.iashchenko.productcatalog.Product;
import pl.iashchenko.productcatalog.ProductStorage;
import pl.iashchenko.productcatalog.SqlProductStorage;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SqlProductStorageTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void helloWorldSql() {
        var sql = """
                select now();
                """;

        var result = jdbcTemplate.queryForObject(sql, String.class);

        assert result.contains("2025");
    }

    @Test
    void itCreateTable() {
        var sql = """
                    CREATE TABLE 'product__catalog' (
                        id varchar(100) not null,
                        name varchar(255) not null,
                        primary key (id),
                    );
                """;

        jdbcTemplate.execute(sql);

        var result = jdbcTemplate.queryForObject("select count(*) from `product__catalog", Integer.class);

        assert result == 0;
    }

    @Test
    void itSaveAndLoadProduct() {
        var product = thereIsProduct();
        var storage = thereIsStorage();

        storage.save(product);

        var loaded = storage.loadProductById(product.getId());

        Assertions.assertEquals(product.getId(), loaded.getId());
        Assertions.assertEquals(product.getDescription(), loaded.getDescription());
    }

    private ProductStorage thereIsStorage() {
        return new SqlProductStorage();
    }

    private Product thereIsProduct() {
        return new Product(UUID.randomUUID(), "test it", "desc");
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
