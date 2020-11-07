package com.productInventory.task;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    @Autowired
    private ProductRepository productRepository;
    //private ProductService productService;

    @PostMapping("/add-product")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
        // return productService.addProduct(product);
    }

    @GetMapping("/search-product")
    public List<Product> searchProduct(@RequestParam(value = "search") String search) {
        List<Product> result = new ArrayList<>();

        for (Product p : productRepository.findAll())
            if (search.equalsIgnoreCase(p.getName()) || search.equalsIgnoreCase(p.getBrand()))
                result.add(p);

        return result;
        //return productService.searchProducts(search);
    }

    @PutMapping("/update-product/{id}")
    public Product updateProduct(@PathVariable(value = "id") long id, @RequestBody Product product) {
        product.setId(id);
        return productRepository.save(product);
        //return productService.updateProduct(id, product);
    }

    @DeleteMapping("/remove-product/{id}")
    public void removeProduct(@PathVariable(value = "id") long id) {
        productRepository.deleteById(id);
        //productService.removeProduct(id);
    }

    @GetMapping("/leftovers")
    public List<Product> getLeftovers() {
        List<Product> result = new ArrayList<>();

        for (Product p : productRepository.findAll())
            if (p.getQuantity() <= 5)
                result.add(p);

        return result;
        //return productService.getLeftovers();
    }
}