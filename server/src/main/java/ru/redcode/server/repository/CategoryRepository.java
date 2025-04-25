package ru.redcode.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redcode.server.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}