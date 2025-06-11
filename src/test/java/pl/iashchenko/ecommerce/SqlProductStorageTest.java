package pl.iashchenko.ecommerce;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pl.iashchenko.productcatalog.Product;
import pl.iashchenko.productcatalog.ProductStorage;
import pl.iashchenko.productcatalog.SqlProductStorage;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SqlProductStorageTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final String EXAMPLE_PRODUCT_NAME = "example product";

    @BeforeEach
    void setupDatabase() {

        jdbcTemplate.execute("DROP TABLE `product_catalog_products` IF EXISTS");

        var sql = """
                    CREATE TABLE `product_catalog_products` (
                        id VARCHAR(100) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        description VARCHAR(100) NOT NULL,
                        cover VARCHAR(100),
                        price DECIMAL(12, 2),
                        PRIMARY KEY (id)
                    )
                """;

        jdbcTemplate.execute(sql);
    }

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
        var result = jdbcTemplate.queryForObject("select count(*) from `product_catalog_products`", Integer.class);

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
        return new SqlProductStorage(jdbcTemplate);
    }

    private Product thereIsProduct() {
        return new Product(UUID.randomUUID(), EXAMPLE_PRODUCT_NAME, "nice one yes yes very nice!!! Horse of lies and deception");
    }


    @Test
    void itLoadsAllProducts() {
        Product product = thereIsProduct();
        ProductStorage storage = thereIsStorage();

        storage.save(product);

        List<Product> all = storage.allProducts();

        assertEquals(1, all.size());
    }

    @Test
    void itAllowsToInsertIntoTable() {
        var sql = """
                INSERT INTO `product_catalog_products`(id, name, description)
                VALUES
                    ('580133b9-a77c-4867-b04c-3d55fabe7ea6', 'product1', 'nice one'),
                    ('580133b9-a77c-4867-b04c-3d55fabe7ea7', 'product2', 'nice two')
                """;

        jdbcTemplate.execute(sql);

        var result = jdbcTemplate.queryForObject("select count(*) from `product_catalog_products`", Integer.class);

        assert result == 2;
    }

    @Test
    void itAllowsToInsertIntoTableWithArguments() {
        var sql = """
                INSERT INTO `product_catalog_products`(id, name, description)
                VALUES
                    (?, ?, ?)
                """;

        jdbcTemplate.update(sql, "580133b9-a77c-4867-b04c-3d55fabe7ea7", "product", "nice two");

        var result = jdbcTemplate.queryForObject("select count(*) from `product_catalog_products`", Integer.class);

        assert result == 1;
    }

    @Test
    void itAllowsToInsertIntoTableWithArgumentsNamedParameters() {
        var sql = """
                INSERT INTO `product_catalog_products`(id, name, description)
                VALUES
                    (:id, :name, :desc)
                """;

        Map<String, Object> params = new HashMap<>();

        params.put("id", "580133b9-a77c-4867-b04c-3d55fabe7ea7");
        params.put("name", "product");
        params.put("desc", "nice two");

        var namedJsdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedJsdbc.update(sql, params);

        var result = jdbcTemplate.queryForObject("select count(*) from `product_catalog_products`", Integer.class);

        assert result == 1;
    }

}
