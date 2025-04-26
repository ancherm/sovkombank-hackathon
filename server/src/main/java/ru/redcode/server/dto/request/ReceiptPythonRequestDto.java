package ru.redcode.server.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    LocalDateTime date;

    @Positive
    @JsonProperty("total_sum")
    BigDecimal total;

    @NotNull
    List<ProductRequestDto> items;

}
