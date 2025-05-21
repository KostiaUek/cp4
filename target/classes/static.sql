 CREATE TABLE `product_catalog_products` (
                        id VARCHAR(100) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        description VARCHAR(100) NOT NULL,
                        cover VARCHAR(100),
                        price DECIMAL(12, 2),
                        PRIMARY KEY (id)
                    )