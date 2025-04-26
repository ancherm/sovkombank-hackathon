package ru.redcode.server.dto.response;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ru.redcode.server.entity.Product}
 */
@Value
public class ProductResponseDto implements Serializable {

    Long id;

    UserResponseDto user;

    String name;

    BigDecimal price;

    Float quantity;

    BigDecimal total;

    String categoryName;



}