package ru.redcode.server.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ru.redcode.server.entity.Product}
 */
@Value
public class ProductRequestDto implements Serializable {

    @JsonProperty("name")
    String name;

    @JsonProperty("receiptId")
    Long receiptId;

    @Min(0)
    @JsonProperty("price")
    BigDecimal price;

    @Min(0)
    @JsonProperty("quantity")
    Float quantity;

    @Min(0)
    @JsonProperty("total")
    BigDecimal total;

    @JsonProperty("category")
    String category;

}