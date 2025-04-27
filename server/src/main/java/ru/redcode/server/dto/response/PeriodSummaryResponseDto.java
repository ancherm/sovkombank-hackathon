package ru.redcode.server.dto.response;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class PeriodSummaryResponseDto {

    BigDecimal totalSum;
    List<ReceiptResponseDto> receipts;

}
