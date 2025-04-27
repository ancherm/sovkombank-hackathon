package ru.redcode.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ru.redcode.server.constant.ServerErrorCode;
import ru.redcode.server.dto.request.ProductRequestDto;
import ru.redcode.server.dto.request.ReceiptPythonRequestDto;
import ru.redcode.server.dto.request.ReceiptRequestDto;
import ru.redcode.server.dto.response.*;
import ru.redcode.server.entity.Category;
import ru.redcode.server.entity.Product;
import ru.redcode.server.entity.Receipt;
import ru.redcode.server.entity.User;
import ru.redcode.server.exception.ServerException;
import ru.redcode.server.mapper.CategorySummaryMapper;
import ru.redcode.server.mapper.ProductMapper;
import ru.redcode.server.mapper.ReceiptMapper;
import ru.redcode.server.repository.CategoryRepository;
import ru.redcode.server.repository.ProductRepository;
import ru.redcode.server.repository.ReceiptRepository;
import ru.redcode.server.repository.UserRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.redcode.server.constant.ServerErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptMapper receiptMapper;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategorySummaryMapper categorySummaryMapper;


    @Transactional
    public ReceiptResponseDto loadReceipt(ReceiptRequestDto receiptRequestDto) {
        User user = userRepository.getUserById(receiptRequestDto.getUserId())
                .orElseThrow(() -> new ServerException(USER_NOT_FOUND));
        log.info("Пользователь с id: {} найден", user.getId());

        Receipt receipt = receiptMapper.toEntity(receiptRequestDto);

        String url = "http://localhost:8000/api/receipts";
        ResponseEntity<ReceiptPythonRequestDto> response;
        ReceiptPythonRequestDto receiptPythonRequestDto;

        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(receiptRequestDto),
                    ReceiptPythonRequestDto.class
            );

            receiptPythonRequestDto = response.getBody();
            log.info("Получен ответ с model-service: {}", receiptPythonRequestDto);
        } catch (HttpServerErrorException.InternalServerError ex) {
            throw new ServerException(RECEIPT_LOAD_EXCEPTION);
        }

        receipt.setDate(receiptPythonRequestDto.getDate());
        receipt.setTotalSum(receiptPythonRequestDto.getTotal());
        receipt.setRetailPlace(receiptPythonRequestDto.getRetailPlace());

        Receipt savedReceipt = receiptRepository.save(receipt);
        log.info("Чек с id: {} успешно сохранен", savedReceipt.getId());

        List<Product> savedProducts = new ArrayList<>();
        for (ProductRequestDto productRequestDto : receiptPythonRequestDto.getItems()) {
            Category category = categoryRepository.findByName(productRequestDto.getCategory())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(productRequestDto.getCategory());
                        return categoryRepository.save(newCategory);
                    });
            log.info("Категория с названием: {}", category.getName());

            Product product = productMapper.toEntity(productRequestDto);
            product.setCategory(category);

            product.setReceipt(savedReceipt);
            product.setUser(user);

            savedProducts.add(productRepository.save(product));
            log.info("Продукт с названием: {} сохранен", product.getName());
        }

        List<ProductResponseDto> itemDtos = productMapper.toDtoList(savedProducts);

        return new ReceiptResponseDto(
                savedReceipt.getId(),
                savedReceipt.getRetailPlace(),
                savedReceipt.getData(),
                savedReceipt.getDate(),
                savedReceipt.getTotalSum(),
                user.getId(),
                itemDtos
        );
    }

    @Transactional
    public List<ReceiptResponseDto> getAllReceipts(Long userId) {
        return receiptMapper.toDtoList(receiptRepository.findAllByUserId(userId));
    }

    public BigDecimal getTotalSum(Long userId) {
        return receiptRepository.sumTotalByUser(userId);
    }

    @Transactional
    public PeriodSummaryResponseDto getSummaryForPeriod(Long userId,
                                                        LocalDate start,
                                                        LocalDate end) {
        LocalDateTime startTime = start.atStartOfDay();
        LocalDateTime endTime = end.atStartOfDay();

        List<Receipt> receipts = receiptRepository.findByUserIdAndDateBetween(userId, startTime, endTime);
        BigDecimal sum = receiptRepository.sumTotalByUserAndPeriod(userId, startTime, endTime);

        List<ReceiptResponseDto> responseDtos = receiptMapper.toDtoList(receipts);
        return new PeriodSummaryResponseDto(sum, responseDtos);
    }

    @Transactional
    public PeriodCategorySummaryResponseDto getCategorySummaryForPeriod(Long userId,
                                                                        LocalDate start,
                                                                        LocalDate end) {
        LocalDateTime startTime = start.atStartOfDay();
        LocalDateTime endTime   = end.atStartOfDay();

        List<ProductRepository.CategorySum> sums =
                productRepository.sumTotalByCategoryAndPeriod(userId, startTime, endTime);

        BigDecimal total = sums.stream()
                .map(ProductRepository.CategorySum::getSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CategorySummaryResponseDto> dtos = sums.stream()
                .map(categorySummaryMapper::toDto)
                .map(dto -> {
                    BigDecimal pct = total.signum() == 0
                            ? BigDecimal.ZERO
                            : dto.getSum()
                            .multiply(BigDecimal.valueOf(100))
                            .divide(total, 2, RoundingMode.HALF_UP);
                    return new CategorySummaryResponseDto(
                            dto.getCategoryName(),
                            dto.getSum(),
                            pct
                    );
                })
                .toList();

        return new PeriodCategorySummaryResponseDto(total, dtos);
    }

}
