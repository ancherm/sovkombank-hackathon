package ru.redcode.server.dto.response;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class PeriodCategorySummaryResponseDto {

    BigDecimal totalSum;

    List<CategorySummaryResponseDto> categories;

}
