package ru.redcode.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.redcode.server.entity.User}
 */
@Value
public class UserRequestDto implements Serializable {
    @NotNull
    @Size(max = 255)
    @NotBlank
    String username;
    @NotNull
    @Size(max = 255)
    @NotBlank
    String password;
}