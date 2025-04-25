package ru.redcode.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redcode.server.entity.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}