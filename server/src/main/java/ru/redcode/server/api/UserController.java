package ru.redcode.server.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redcode.server.dto.request.UserRequestDto;
import ru.redcode.server.dto.response.UserResponseDto;
import ru.redcode.server.service.UserService;

@Slf4j
@RestController
@RequestMapping("api/users")
//@Tag(name = "Пользователи", description = "Управление пользователями")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{ID}")
//    @Operation(summary = "Получить пользователя по ID", description = "Возвращает данные пользователя")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("ID") long id) {
        log.info("Запрос на получение пользователя с id: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

//    @PostMapping
//    @Operation(summary = "Создание пользователя")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        log.info("Запрос на получение пользователя с username: {}", userRequestDto.getUsername());
        return ResponseEntity.ok(userService.createUser(userRequestDto));
    }

}
