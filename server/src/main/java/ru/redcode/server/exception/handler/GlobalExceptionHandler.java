package ru.redcode.server.exception.handler;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.redcode.server.dto.response.ErrorResponseDto;
import ru.redcode.server.exception.ServerException;

@Slf4j
@Tag(name = "Обработчик исключений")
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponseDto(ex.getBindingResult().getFieldErrors()));
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorResponseDto> handleServerException(ServerException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getServerErrorCode());

        log.info("Ошибка на сервере {}", ex.getServerErrorCode());

        return ResponseEntity.badRequest().body(errorResponseDto);
    }

}
