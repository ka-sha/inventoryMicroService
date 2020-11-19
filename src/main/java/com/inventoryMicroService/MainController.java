package com.inventoryMicroService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    @Autowired
    private ProductRepository productRepository;

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
        List<Product> result = new ArrayList<>();

        for (Product p : productRepository.findAll())
            if (search.equalsIgnoreCase(p.getName()) || search.equalsIgnoreCase(p.getBrand()))
                result.add(p);

        return result;
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
        List<Product> result = new ArrayList<>();

        for (Product p : productRepository.findAll())
            if (p.getQuantity() <= 5)
                result.add(p);

        return result;
    }
}
