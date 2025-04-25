package ru.redcode.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redcode.server.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}