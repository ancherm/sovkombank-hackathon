package ru.redcode.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redcode.server.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    interface CategorySum {
        String getCategoryName();

        BigDecimal getSum();
    }

    @Query("""
              SELECT p.category.name     AS categoryName,
                     SUM(p.total)        AS sum
              FROM Product p
              WHERE p.user.id = :userId
                AND p.receipt.date BETWEEN :start AND :end
              GROUP BY p.category.name
            """)
    List<CategorySum> sumTotalByCategoryAndPeriod(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

}