package it.kevien.demo.productservice.model.dto;

import java.math.BigDecimal;

public record ProductRequest(String name, String description, BigDecimal price) {

}
