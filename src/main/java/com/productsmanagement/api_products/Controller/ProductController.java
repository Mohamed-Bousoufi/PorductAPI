  package com.productsmanagement.api_products.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.productsmanagement.api_products.Model.Product;
import com.productsmanagement.api_products.Repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductRepository proRepository;


    public ProductController(ProductRepository proRepository) {
        this.proRepository = proRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> allPro =  proRepository.findAll();
        if(allPro.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allPro);
    }

    @GetMapping("/")
    public ResponseEntity<Product> findByName(@RequestParam String name) {
        Optional<Product> pro = Optional.ofNullable(proRepository.findByName(name));
        if (pro.isPresent()) {
            return ResponseEntity.ok().body(pro.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("createProduct")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Optional<Product> pro = Optional.ofNullable(proRepository.findByName(product.getName()));
        if (pro.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
                proRepository.save(product);
                return ResponseEntity.ok().body(product);
    }
    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestParam String name , @Valid @RequestBody Product product) {
       Optional<Product> pro = Optional.ofNullable(proRepository.findByName(name));
       if (pro.isPresent()) {
           Product updatedProd = pro.get();
           updatedProd.setName(product.getName());
           updatedProd.setPrice(product.getPrice());
           updatedProd.setDescription(product.getDescription());
           this.proRepository.save(updatedProd);
           return ResponseEntity.ok().body(updatedProd);
       }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam String name) {
        Optional<Product> pro = Optional.ofNullable(proRepository.findByName(name));
        if (pro.isPresent()) {
            proRepository.delete(pro.get());
            return ResponseEntity.ok().body(pro.get());
        }
        return ResponseEntity.notFound().build();
    }

}
