package ru.redcode.server.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServerErrorCode {

    VALIDATION_ERROR("Ошибка валидации", ""),
    ILLEGAL_ACTION("Недопустимое действие", ""),
    USERNAME_ALREADY_EXISTS("Username уже существует", "username"),
    USER_NOT_FOUND("Пользователя не существует", "id"),
    RECEIPT_LOAD_EXCEPTION("Ошибка при загрузке чека", "receipt"),
    DATE_FORMAT_EXCEPTION("Неверный формат даты", "date"),
    ;

    private final String message;
    private final String field;

    public String format(Object...args) {
        return String.format(message, args);
    }

}
