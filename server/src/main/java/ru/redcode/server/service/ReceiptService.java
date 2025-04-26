package ru.redcode.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.redcode.server.constant.ServerErrorCode;
import ru.redcode.server.dto.request.ProductRequestDto;
import ru.redcode.server.dto.request.ReceiptRequestDto;
import ru.redcode.server.dto.response.ProductResponseDto;
import ru.redcode.server.entity.Category;
import ru.redcode.server.entity.Product;
import ru.redcode.server.entity.Receipt;
import ru.redcode.server.entity.User;
import ru.redcode.server.exception.ServerException;
import ru.redcode.server.mapper.ProductMapper;
import ru.redcode.server.mapper.ReceiptMapper;
import ru.redcode.server.repository.CategoryRepository;
import ru.redcode.server.repository.ProductRepository;
import ru.redcode.server.repository.ReceiptRepository;
import ru.redcode.server.repository.UserRepository;

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


    @Transactional
    public List<ProductResponseDto> loadReceipt(ReceiptRequestDto receiptRequestDto) {
        Receipt receipt = receiptMapper.toEntity(receiptRequestDto);
        log.info("Чек с id: {} успешно сохранен", receipt.getId());

        String url = "http://localhost:8000/api/receipts";

        ResponseEntity<List<ProductRequestDto>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(receiptRequestDto),
                new ParameterizedTypeReference<>() {}
        );

        List<ProductRequestDto> productList = response.getBody();
        log.info("Получен ответ с model-service: {}", productList);

        User user = userRepository.getUserById(receiptRequestDto.getUserId())
                .orElseThrow(() -> new ServerException(USER_NOT_FOUND));
        log.info("Пользователь с id: {} найден", user.getId());

        List<Product> savedProducts = new ArrayList<>();
        for (ProductRequestDto dto : productList) {
            Category category = categoryRepository.findByName(dto.getCategoryName())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(dto.getCategoryName());
                        return categoryRepository.save(newCategory);
                    });
            log.info("Категория с названием: {}", category.getName());

            Product product = new Product();
            product.setName(dto.getName());
            product.setPrice(dto.getPrice());
            product.setUser(user);
            product.setReceipt(receipt);
            product.setCategory(category);

            savedProducts.add(productRepository.save(product));
            log.info("Продукт с названием: {} сохранен", product.getName());
        }

        return savedProducts.stream()
                .map(productMapper::toDto)
                .toList();
    }

}
