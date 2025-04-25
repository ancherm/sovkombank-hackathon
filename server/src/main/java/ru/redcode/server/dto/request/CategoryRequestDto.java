package ru.redcode.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.redcode.server.entity.Category}
 */
@Value
public class CategoryRequestDto implements Serializable {
    @NotBlank
    String name;
}