package ru.redcode.server.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link ru.redcode.server.entity.Receipt}
 */
@Value
public class ReceiptResponseDto implements Serializable {

    Long id;

    String retailPlace;

    String data;

    LocalDateTime date;

    BigDecimal totalSum;

    Long user;

    List<ProductResponseDto> items;

}