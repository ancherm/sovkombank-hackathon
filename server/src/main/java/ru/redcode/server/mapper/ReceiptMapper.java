package ru.redcode.server.mapper;

import org.mapstruct.*;
import ru.redcode.server.dto.request.ReceiptPythonRequestDto;
import ru.redcode.server.dto.request.ReceiptRequestDto;
import ru.redcode.server.dto.response.ReceiptResponseDto;
import ru.redcode.server.entity.Receipt;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface ReceiptMapper {
//    @Mapping(source = "userPasswordHash", target = "user.passwordHash")
//    @Mapping(source = "userUsername", target = "user.username")
    @Mapping(source = "userId", target = "user.id")
    Receipt toEntity(ReceiptRequestDto receiptRequestDto);

    @InheritInverseConfiguration(name = "toEntity")
    ReceiptRequestDto toDto(Receipt receipt);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Receipt partialUpdate(ReceiptRequestDto receiptRequestDto, @MappingTarget Receipt receipt);

    Receipt toEntity(ReceiptResponseDto receiptResponseDto);

    ReceiptResponseDto toDto1(Receipt receipt);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Receipt partialUpdate(ReceiptResponseDto receiptResponseDto, @MappingTarget Receipt receipt);


    Receipt toEntity(ReceiptPythonRequestDto receiptPythonRequestDto);
}
