package ru.redcode.server.mapper;

import org.mapstruct.*;
import ru.redcode.server.dto.request.ReceiptPythonRequestDto;
import ru.redcode.server.dto.request.ReceiptRequestDto;
import ru.redcode.server.dto.response.ReceiptResponseDto;
import ru.redcode.server.entity.Receipt;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface ReceiptMapper {
    @Mapping(source = "userId", target = "user.id")
    Receipt toEntity(ReceiptRequestDto receiptRequestDto);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Receipt partialUpdate(ReceiptRequestDto receiptRequestDto, @MappingTarget Receipt receipt);

    @Mapping(source = "user.id", target = "user")
    ReceiptResponseDto toDto(Receipt receipt);

    List<ReceiptResponseDto> toDtoList(List<Receipt> receipts);

    Receipt toEntity(ReceiptPythonRequestDto receiptPythonRequestDto);
}
