package com.example.spring2023.controllers;

import com.example.spring2023.models.LoreCategory;
import com.example.spring2023.repo.LoreCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/lore")
@Controller
public class LoreCategoryController {
    private final LoreCategoryRepository repository;

    LoreCategoryController(LoreCategoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/{category_name}")
    public ResponseEntity<String> addCategory(@PathVariable String category_name, @RequestBody LoreCategory category) {
        category.setName(category_name);
        repository.save(category);
        return ResponseEntity.ok("Category added.");
    }

    @PostMapping("/{category_name}/update")
    public ResponseEntity<String> updateCategory(@PathVariable String category_name, @RequestBody LoreCategory updatedCategory) {
        LoreCategory existingCategory = repository.findByName(category_name);
        if(existingCategory != null){
            existingCategory.setName(updatedCategory.getName());
            repository.save(existingCategory);
            return ResponseEntity.ok("Category updated.");
        }else{
            return ResponseEntity.badRequest().body("Category not found.");
        }
    }

    @GetMapping("/{category_name}/delete")
    public ResponseEntity<String> deleteCategory(@PathVariable String category_name) {
        LoreCategory existingCategory = repository.findByName(category_name);
        if(existingCategory != null){
            repository.delete(existingCategory);
            return ResponseEntity.ok("Category deleted.");
        }else{
            return ResponseEntity.badRequest().body("Category not found.");
        }
    }
}
    //Я хотел бы получить ваши рекомендации по тому как правильнее реализовать данный способ для создания категорий и управления топиками
    //Я решил создать довольно интересную задумку, чтобы модератор сам решал какие категории ему нужны в лоре, так как это дело каждого
    //Так что здесь я не создаю чёткого ограничения, и модератор вправе сам создавать новые категории, топики в них и в них уже создавать какие либо сущности
    //В качестве примера модератор создаёт категорию "Боги" и в ней создаёт топик "Бог войны", "Богиня магии" и т.д.