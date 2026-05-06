package it.kevien.demo.productservice.service;

import it.kevien.demo.productservice.exception.ProductNotFoundException;
import it.kevien.demo.productservice.model.Product;
import it.kevien.demo.productservice.model.dto.ProductRequest;
import it.kevien.demo.productservice.model.dto.ProductResponse;
import it.kevien.demo.productservice.model.mapper.ProductMapper;
import it.kevien.demo.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products);
    }

    public ProductResponse getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(productMapper::toDto).orElseThrow(() -> new ProductNotFoundException("Product with id '" + id + "' not found"));
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            Product product = opt.get();
            product.setName(productRequest.name());
            product.setDescription(productRequest.description());
            product.setPrice(productRequest.price());
            productRepository.save(product);
            return productMapper.toDto(product);
        }
        throw new ProductNotFoundException("Product with id '" + id + "' not found");
    }

    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return;
        }
        throw new ProductNotFoundException("Product with id '" + id + "' not found");
    }
}
