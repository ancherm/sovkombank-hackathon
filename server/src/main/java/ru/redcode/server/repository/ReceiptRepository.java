package ru.redcode.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redcode.server.entity.Receipt;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Page<Receipt> findByUserIdAndRetailPlaceContainingIgnoreCase(Long userId, String retailPlace, Pageable pageable);

    List<Receipt> findByUserIdAndDateBetween(Long userId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT COALESCE(SUM(r.totalSum), 0) FROM Receipt r WHERE r.user.id = :userId")
    BigDecimal sumTotalByUser(@Param("userId") Long userId);

    @Query("""
            SELECT COALESCE(SUM(r.totalSum), 0)
            FROM Receipt r
            WHERE r.user.id = :userId
              AND r.date BETWEEN :start AND :end
            """)
    BigDecimal sumTotalByUserAndPeriod(@Param("userId") Long userId,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);



}