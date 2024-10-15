package com.boot.controller;

import com.boot.entity.Product;
import com.boot.exception.ProductNotFoundException;
import com.boot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint working");
    }

    @PostMapping("/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.fetchAllProducts();
        return ResponseEntity.ok(products);
    }
    //
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.fetchProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id)));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id)));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.ok("Product with ID " + id + " has been deleted successfully");
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }
    /** extra codes --
    //  @GetMapping("/product/{id}")
    //    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    //        Optional<Product> productOptional = productService.fetchProductById(id);
    //        return productOptional.map(ResponseEntity::ok)
    //                .orElseGet(() -> ResponseEntity.notFound().build());
    //    }
    //
    //    @PutMapping(path = "/product/{productId}")
    //    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
    //        Optional<Product> updatedProductOptional = productService.updateProduct(id, product);
    //        return updatedProductOptional.map(ResponseEntity::ok)
    //                .orElseGet(() -> ResponseEntity.notFound().build());
    //    }
    //
    //    @DeleteMapping(value = "/product/{productId}")
    //    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
    //        boolean deletionStatus = productService.deleteProduct(id);
    //        if (deletionStatus) {
    //            return ResponseEntity.ok("Product with ID " + id + " has been deleted successfully");
    //        } else {
    //            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product with ID " + id);
    //        }
       }
     **/


}