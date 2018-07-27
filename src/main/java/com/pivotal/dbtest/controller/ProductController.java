package com.pivotal.dbtest.controller;

import com.pivotal.dbtest.Repository.ProductRepository;
import com.pivotal.dbtest.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public @ResponseBody Iterable<Product> getProducts() {
        Iterable<Product> products = repository.findAll();

        return products;
    }

    @PostMapping
    public String addProduct(@RequestBody Product product) {
        return String.format("Successfully saved product %s - %s",
                product.getName(),
                repository.save(product).getId().toString());
    }
}
