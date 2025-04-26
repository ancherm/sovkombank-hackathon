package ru.redcode.server.dto.response;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.redcode.server.entity.User}
 */
@Value
public class UserResponseDto implements Serializable {

    Long id;

    String username;

    String userType;

}