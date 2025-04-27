package ru.redcode.server.mapper;

import org.mapstruct.*;
import ru.redcode.server.dto.request.ProductRequestDto;
import ru.redcode.server.dto.response.ProductResponseDto;
import ru.redcode.server.entity.Product;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface ProductMapper {
//    @Mapping(source = "receiptId", target = "receipt.id")
//    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "category", target = "category.name")
    Product toEntity(ProductRequestDto productRequestDto);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "user",          target = "user")
    ProductResponseDto toDto(Product product);

    List<ProductResponseDto> toDtoList(List<Product> products);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductRequestDto productRequestDto, @MappingTarget Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductResponseDto productResponseDto, @MappingTarget Product product);

}
