package com.inventoryMicroService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/all-products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/add-product")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/search-product")
    public List<Product> searchProduct(@RequestParam(value = "search") String search) {
        search = search.toLowerCase();
        String sql = "SELECT * " +
                "FROM product " +
                "WHERE LOWER(brand) = ? " +
                "OR LOWER(name) = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), search, search);
    }

    @PutMapping("/update-product/{id}")
    public Product updateProduct(@PathVariable(value = "id") long id, @RequestBody Product product) {
        if (!productRepository.findById(id).isPresent())
            throw new ProductNotFoundException("There is no product with such id");

        product.setId(id);
        return productRepository.save(product);
    }

    @DeleteMapping("/remove-product/{id}")
    public void removeProduct(@PathVariable(value = "id") long id) {
        if (!productRepository.findById(id).isPresent())
            throw new ProductNotFoundException("There is no product with such id");

        productRepository.deleteById(id);
    }

    @GetMapping("/leftovers")
    public List<Product> getLeftovers() {
        String sql = "SELECT * " +
                "FROM product " +
                "WHERE quantity <= 5";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }
}