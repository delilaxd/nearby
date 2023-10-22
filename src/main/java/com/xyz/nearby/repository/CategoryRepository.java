package com.xyz.nearby.repository;

import com.xyz.nearby.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
