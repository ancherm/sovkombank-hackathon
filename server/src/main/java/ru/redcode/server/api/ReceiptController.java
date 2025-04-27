package ru.redcode.server.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redcode.server.dto.request.ReceiptRequestDto;
import ru.redcode.server.dto.response.PeriodCategorySummaryResponseDto;
import ru.redcode.server.dto.response.PeriodSummaryResponseDto;
import ru.redcode.server.dto.response.ReceiptResponseDto;
import ru.redcode.server.service.ReceiptService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<ReceiptResponseDto> loadReceipt(@RequestBody @Valid ReceiptRequestDto requestDto) {
        log.info("Запрос на загрузку чека от пользователя с id: {}", requestDto.getUserId());
        return ResponseEntity.ok(receiptService.loadReceipt(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<ReceiptResponseDto>> listReceipts(@RequestParam Long userId) {
        if (userId < 1) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(receiptService.getAllReceipts(userId));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotal(@RequestParam Long userId) {
        log.info("Запрос на получение суммы трат пользователя с id: {}", userId);
        return ResponseEntity.ok(receiptService.getTotalSum(userId));
    }

    @GetMapping("/summary")
    public ResponseEntity<PeriodSummaryResponseDto> getSummary(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        log.info("Запрос на подсчет трат с параметрами: userId {}, start {}, end {}", userId, start, end);
        return ResponseEntity.ok(receiptService.getSummaryForPeriod(userId, start, end));
    }

    @GetMapping("/summary/categories")
    public ResponseEntity<PeriodCategorySummaryResponseDto> getCategorySummary(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        PeriodCategorySummaryResponseDto dto =
                receiptService.getCategorySummaryForPeriod(userId, start, end);
        return ResponseEntity.ok(dto);
    }

}

