package ru.redcode.server.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.redcode.server.dto.request.ReceiptRequestDto;
import ru.redcode.server.dto.response.ProductResponseDto;
import ru.redcode.server.dto.response.ReceiptResponseDto;
import ru.redcode.server.service.ReceiptService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<List<ProductResponseDto>> loadReceipt(@RequestBody @Valid ReceiptRequestDto requestDto) {
        log.info("Запрос на загрузку чека от пользователя с id: {}", requestDto.getUserId());
        return ResponseEntity.ok(receiptService.loadReceipt(requestDto));
    }
}
