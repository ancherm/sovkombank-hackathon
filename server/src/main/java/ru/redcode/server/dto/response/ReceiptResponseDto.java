package ru.redcode.server.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.redcode.server.entity.Receipt}
 */
@Value
public class ReceiptResponseDto implements Serializable {

    Long id;

    UserResponseDto user;

    String data;

}