package it.kevien.demo.productservice.controller;

import it.kevien.demo.productservice.model.dto.ProductRequest;
import it.kevien.demo.productservice.model.dto.ProductResponse;
import it.kevien.demo.productservice.service.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest product, @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
