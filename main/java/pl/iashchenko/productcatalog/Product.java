package pl.iashchenko.productcatalog;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private final UUID uuid;
    private final String name;
    private final String description;
    private String image;
    private BigDecimal price;

    public Product(UUID uuid, String name, String description) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return uuid.toString();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void changePrice(BigDecimal bigDecimal) {
        this.price = bigDecimal;
    }

    public void setImage(String url) {
        this.image = url;
    }

    public String getImage() {
        return image;
    }
}
