package ru.redcode.server.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ru.redcode.server.entity.Product}
 */
@Value
public class ProductResponseDto implements Serializable {
    Long id;
    @NotNull
    UserResponseDto user;
    @Size(max = 255)
    String name;
    BigDecimal price;
    @Size(max = 255)
    String categoryName;
}