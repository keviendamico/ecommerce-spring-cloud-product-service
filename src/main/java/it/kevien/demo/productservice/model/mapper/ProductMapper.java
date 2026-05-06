package it.kevien.demo.productservice.model.mapper;

import it.kevien.demo.productservice.model.Product;
import it.kevien.demo.productservice.model.dto.ProductRequest;
import it.kevien.demo.productservice.model.dto.ProductResponse;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequest product);
    ProductResponse toDto(Product product);
    List<ProductResponse> toDtoList(List<Product> products);
}
