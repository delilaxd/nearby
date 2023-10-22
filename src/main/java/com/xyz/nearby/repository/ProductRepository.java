package com.xyz.nearby.repository;

import com.xyz.nearby.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR p.category.name LIKE %:category%)" +
            "AND (p.available = true)"+
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "ORDER BY " +
            "   CASE WHEN :sortBy = 'price-asc' THEN p.price END ASC, " +
            "   CASE WHEN :sortBy = 'price-desc' THEN p.price END DESC, " +
            "   CASE WHEN :sortBy = 'name-asc' THEN p.name END ASC, " +
            "   CASE WHEN :sortBy = 'name-desc' THEN p.name END DESC")
    List<Product> findAvailableProducts(@Param("category") String category,
                                        @Param("minPrice") Double minPrice,
                                        @Param("maxPrice") Double maxPrice,
                                        @Param("sortBy") String sortBy);

}
