package com.productsmanagement.api_products.Repository;

import com.productsmanagement.api_products.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPrice(Double price);
    Product findByName(String name);

}
