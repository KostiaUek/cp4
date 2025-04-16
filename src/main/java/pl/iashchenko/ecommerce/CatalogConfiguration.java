package pl.iashchenko.ecommerce;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.iashchenko.productcatalog.ArrayListProductStorage;
import pl.iashchenko.productcatalog.ProductCatalog;

@Configuration
public class CatalogConfiguration {

    @Bean
    ProductCatalog createMyProductCatalog () {
        var catalog = new ProductCatalog(new ArrayListProductStorage());

        catalog.createProduct("nice one", "nic edesc");
        catalog.createProduct("nice one2", "nic edesc");
        catalog.createProduct("nice one3", "nic edesc");

        return catalog;
    }
}
