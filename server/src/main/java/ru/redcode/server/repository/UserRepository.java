package ru.redcode.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redcode.server.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserById(Long id);


}