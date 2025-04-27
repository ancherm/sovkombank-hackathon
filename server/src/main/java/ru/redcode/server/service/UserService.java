package ru.redcode.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.redcode.server.constant.ServerErrorCode;
import ru.redcode.server.dto.request.UserRequestDto;
import ru.redcode.server.dto.response.UserResponseDto;
import ru.redcode.server.entity.User;
import ru.redcode.server.exception.ServerException;
import ru.redcode.server.mapper.UserMapper;
import ru.redcode.server.repository.UserRepository;

import static ru.redcode.server.constant.ServerErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserResponseDto getUserById(long id) {
        User user = userRepository.getUserById(id)
                .orElseThrow(() -> new ServerException(USER_NOT_FOUND));
        log.info("Найден пользователь с id {}", id);

        return userMapper.toDto(user);
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);

        String hashPassword = BCrypt.hashpw(userRequestDto.getPassword(), BCrypt.gensalt(12));
        user.setPasswordHash(hashPassword);

        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (Exception ex) {
            throw new ServerException(USERNAME_ALREADY_EXISTS);
        }
        log.info("Пользователь с username: {} сохранен с id: {}", savedUser.getUsername(), savedUser.getId());

        return userMapper.toDto(savedUser);
    }

}
