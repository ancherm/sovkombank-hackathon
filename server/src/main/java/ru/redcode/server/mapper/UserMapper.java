package ru.redcode.server.mapper;

import org.mapstruct.*;
import ru.redcode.server.dto.request.UserRequestDto;
import ru.redcode.server.dto.response.UserResponseDto;
import ru.redcode.server.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserRequestDto userRequestDto, @MappingTarget User user);

    User toEntity(UserResponseDto userResponseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserResponseDto userResponseDto, @MappingTarget User user);

//    UserResponseDto toDto1(User user);

}
