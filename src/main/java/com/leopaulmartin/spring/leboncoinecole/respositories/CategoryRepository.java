package com.leopaulmartin.spring.leboncoinecole.respositories;

import com.leopaulmartin.spring.leboncoinecole.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
