package ru.redcode.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.redcode.server.dto.response.CategorySummaryResponseDto;
import ru.redcode.server.repository.ProductRepository.CategorySum;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategorySummaryMapper {
    CategorySummaryResponseDto toDto(CategorySum proj);
}
