package ru.redcode.server.dto.response;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CategorySummaryResponseDto {

    String categoryName;

    BigDecimal sum;

    BigDecimal percentage;

}
