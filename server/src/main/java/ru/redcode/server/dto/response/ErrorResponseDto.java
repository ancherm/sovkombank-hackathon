package ru.redcode.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import ru.redcode.server.constant.ServerErrorCode;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDto {

    private List<ErrorItem> errors = new ArrayList<>();


    public ErrorResponseDto(List<FieldError> fieldErrors) {
        for (FieldError fieldError : fieldErrors) {
            String message = fieldError.getDefaultMessage();

            log.info("Ошибка валидации: {}", message);
            errors.add(new ErrorItem(ServerErrorCode.VALIDATION_ERROR.name(), fieldError.getField(), message));
        }
    }

    public ErrorResponseDto(ServerErrorCode serverErrorCode) {
        errors.add(new ErrorItem(serverErrorCode.name(), serverErrorCode.getField(), serverErrorCode.getMessage()));
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class ErrorItem {

        private String errorCode;
        private String field;
        private String message;

    }

}
