package ru.redcode.server.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.hibernate.query.SelectionQuery;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ru.redcode.server.entity.Product}
 */
@Value
public class ProductRequestDto implements Serializable {

    @JsonProperty("userId")
    Long userId;

    @JsonProperty("receiptId")
    Long receiptId;

    @JsonProperty("name")
    @Size(max = 255)
    String name;

    @JsonProperty("price")
    BigDecimal price;

    @JsonProperty("categoryName")
    String categoryName;

}