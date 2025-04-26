package ru.redcode.server.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Map<String, Object>> listReceipts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String searchQuery,
            @RequestParam Long userId
    ) {
        Page<Receipt> pageResult = receiptService.getReceipts(userId, searchQuery, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("receipts", pageResult.getContent());
        response.put("pagination", Map.of(
                "currentPage", pageResult.getNumber() + 1,
                "totalPages", pageResult.getTotalPages(),
                "totalReceipts", pageResult.getTotalElements()
        ));

        return ResponseEntity.ok(response);
    }
}
