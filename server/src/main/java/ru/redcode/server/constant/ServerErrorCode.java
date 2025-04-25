package ru.redcode.server.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServerErrorCode {

    VALIDATION_ERROR("Ошибка валидации", ""),
    ILLEGAL_ACTION("Недопустимое действие", ""),

//    AlreadyExist
    LOGIN_ALREADY_EXISTS("Логин уже существует", "login"),

//    NotFound
    USER_NOT_FOUND("Пользователя не существует", "login"),
    ;

    private final String message;
    private final String field;

    public String format(Object...args) {
        return String.format(message, args);
    }

}
