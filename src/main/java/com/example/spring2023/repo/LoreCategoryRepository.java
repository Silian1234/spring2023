package com.example.spring2023.repo;

import com.example.spring2023.models.LoreCategory;
import org.springframework.data.repository.CrudRepository;

public interface LoreCategoryRepository extends CrudRepository<LoreCategory, Long> {
    LoreCategory findByName(String categoryName);
}