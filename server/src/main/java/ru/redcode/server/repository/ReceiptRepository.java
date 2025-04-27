package ru.redcode.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redcode.server.entity.Receipt;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Page<Receipt> findByUserIdAndRetailPlaceContainingIgnoreCase(Long userId, String retailPlace, Pageable pageable);


    List<Receipt> findAllByUserId(Long userId);
}