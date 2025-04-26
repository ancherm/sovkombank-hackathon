package ru.redcode.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class ReceiptPythonRequestDto {

    @NotBlank
    String retailPlace;

    @Positive
    BigDecimal totalSum;

    LocalDateTime date;

    @NotNull
    List<ProductRequestDto> items;

}
