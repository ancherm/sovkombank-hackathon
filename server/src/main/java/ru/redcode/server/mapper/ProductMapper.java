package ru.redcode.server.mapper;

import org.mapstruct.*;
import ru.redcode.server.dto.request.ProductRequestDto;
import ru.redcode.server.dto.response.ProductResponseDto;
import ru.redcode.server.entity.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface ProductMapper {
    @Mapping(source = "receiptId", target = "receipt.id")
    @Mapping(source = "userId", target = "user.id")
    Product toEntity(ProductRequestDto productRequestDto);

    @InheritInverseConfiguration(name = "toEntity")
    ProductRequestDto toDto(Product product);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductRequestDto productRequestDto, @MappingTarget Product product);

    Product toEntity(ProductResponseDto productResponseDto);

    ProductResponseDto toDto1(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductResponseDto productResponseDto, @MappingTarget Product product);
}
