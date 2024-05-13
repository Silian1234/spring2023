package com.example.spring2023.controllers;

import com.example.spring2023.models.LoreCategory;
import com.example.spring2023.repo.LoreCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// Определение основного URL
@RequestMapping("/api/lore")
@RestController
public class LoreCategoryController {

    // Объявление репозитория
    private final LoreCategoryRepository repository;

    // Конструктор: инициализация репозитория
    LoreCategoryController(LoreCategoryRepository repository) {
        this.repository = repository;
    }
    // Получение всех категорий
    @GetMapping("/")
    public ResponseEntity<Iterable<LoreCategory>> getCategories() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Получение категории по ID
    @GetMapping("/{category_id}")
    public ResponseEntity<LoreCategory> getCategory(@PathVariable Long category_id) {
        Optional<LoreCategory> existingCategoryOpt = repository.findById(category_id);

        // Если категория не найдена, ответ - 404
        if (!existingCategoryOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Возврат найденной категории
        return ResponseEntity.ok(existingCategoryOpt.get());
    }

    // Добавление новой категории
    @PostMapping("/")
    public ResponseEntity<LoreCategory> addCategory(@RequestBody LoreCategory category) {
        LoreCategory newCategory = new LoreCategory();
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        repository.save(newCategory);
        return ResponseEntity.ok(newCategory);
    }

    // Обновление существующей категории
    @PutMapping("/{category_id}")
    public ResponseEntity<LoreCategory> updateCategory(@PathVariable Long category_id, @RequestBody LoreCategory updatedCategory) {
        Optional<LoreCategory> existingCategoryOpt = repository.findById(category_id);

        // Если категория не найдена, ответ - 404
        if (!existingCategoryOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Обновление поля "name" и "description" найденной категории
        LoreCategory existingCategory = existingCategoryOpt.get();
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());
        repository.save(existingCategory);
        return ResponseEntity.ok(existingCategory);
    }

    // Удаление категории
    @DeleteMapping("/{category_id}")
    public ResponseEntity deleteCategory(@PathVariable Long category_id) {
        Optional<LoreCategory> existingCategoryOpt = repository.findById(category_id);

        // Если категория не найдена, ответ - 404
        if (!existingCategoryOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Удаление найденной категории
        repository.deleteById(category_id);
        return ResponseEntity.ok().build();
    }
}