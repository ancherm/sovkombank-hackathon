package ru.redcode.server.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redcode.server.dto.request.ReceiptRequestDto;
import ru.redcode.server.dto.response.ProductResponseDto;
import ru.redcode.server.dto.response.ReceiptResponseDto;
import ru.redcode.server.entity.Receipt;
import ru.redcode.server.service.ReceiptService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
