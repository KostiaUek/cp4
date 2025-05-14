package pl.iashchenko.productcatalog;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SqlProductStorage implements ProductStorage {

    private final JdbcTemplate jdbcTemplate;

    public SqlProductStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.setupDatabase();
    }

    private void setupDatabase() {

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

    @Override
    public List<Product> allProducts() {
        // SQL --> product
        var sql = "select * from `product_catalog_products`";

        var result = jdbcTemplate.query(sql, new Object[]{}, (rs, i) -> {
            var product = new Product(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("description"));
            return product;
        });

        return result;
    }

    @Override
    public void save(Product newProduct) {
        // if already exists

        var sql = """
                INSERT INTO `product_catalog_products`(id, name, description)
                VALUES
                    (:id, :name, :desc)
                """;

        Map<String, Object> params = new HashMap<>();

        params.put("id", newProduct.getId());
        params.put("name", newProduct.getName());
        params.put("desc", newProduct.getDescription());

        var namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedJdbc.update(sql, params);
    }

    @Override
    public Product loadProductById(String productId) {
        // SQL --> product
        var sql = "select * from `product_catalog_products` where id = ?";

        var result = jdbcTemplate.queryForObject(sql, new Object[]{productId}, (rs, i) -> {
            var product = new Product(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("description"));
            return product;
        });


        return result;
    }
}
