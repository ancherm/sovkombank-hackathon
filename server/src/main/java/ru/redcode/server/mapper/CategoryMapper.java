package ru.redcode.server.mapper;

import org.mapstruct.*;
import ru.redcode.server.dto.request.CategoryRequestDto;
import ru.redcode.server.entity.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface CategoryMapper {
    Category toEntity(CategoryRequestDto categoryRequestDto);

    CategoryRequestDto toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryRequestDto categoryRequestDto, @MappingTarget Category category);
}
