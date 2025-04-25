package ru.redcode.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.redcode.server.entity.Receipt}
 */
@Value
public class ReceiptRequestDto implements Serializable {
    Long userId;

    @NotNull
    @NotBlank
    String data;
}