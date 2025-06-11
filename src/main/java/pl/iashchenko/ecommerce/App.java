package pl.iashchenko.ecommerce;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.iashchenko.productcatalog.ArrayListProductStorage;
import pl.iashchenko.productcatalog.ProductCatalog;

@SpringBootApplication
public class App {
    public static void main(String[] args){
        System.out.println("It works!");
        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createMyProductCatalog () {
        var catalog = new ProductCatalog(new ArrayListProductStorage());

        catalog.createProduct("nice one", "nic edesc");

        return catalog;
    }
}
