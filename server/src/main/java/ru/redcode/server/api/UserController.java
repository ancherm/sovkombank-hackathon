package ru.redcode.server.api;

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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{ID}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("ID") long id) {
        log.info("Запрос на получение пользователя с id: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        log.info("Запрос на получение пользователя с username: {}", userRequestDto.getUsername());
        return ResponseEntity.ok(userService.createUser(userRequestDto));
    }

}
