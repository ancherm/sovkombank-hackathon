package ru.redcode.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.redcode.server.constant.ServerErrorCode;

@Getter
@AllArgsConstructor
public class ServerException extends RuntimeException {

    private ServerErrorCode serverErrorCode;

}
