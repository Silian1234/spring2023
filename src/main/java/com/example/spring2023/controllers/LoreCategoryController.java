package com.example.spring2023.controllers;

import com.example.spring2023.models.LoreCategory;
import com.example.spring2023.repo.LoreCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Примечание к маршруту для этого контроллера
@RequestMapping("/lore")
@Controller
public class LoreCategoryController {
    // Инициализация репозитория
    private final LoreCategoryRepository repository;

    // Конструктор с инъекцией репозитория
    LoreCategoryController(LoreCategoryRepository repository) {
        this.repository = repository;
    }

    // Метод для добавления категории
    @PostMapping("/{category_name}")
    public ResponseEntity<String> addCategory(@PathVariable String category_name, @RequestBody LoreCategory category) {
        LoreCategory newCategory = new LoreCategory();
        newCategory.setName(category_name);
        newCategory.setDescription(category.getDescription()); // Устанавливаем описание из запроса
        repository.save(newCategory);
        return ResponseEntity.ok("Категория добавлена.");
    }


    // Метод для обновления категории
    @PostMapping("/{category_name}/update")
    public ResponseEntity<String> updateCategory(@PathVariable String category_name, @RequestBody LoreCategory updatedCategory) {
        LoreCategory existingCategory = repository.findByName(category_name);

        if (existingCategory != null) {
            existingCategory.setName(updatedCategory.getName());
            existingCategory.setDescription(updatedCategory.getDescription());  // Обновляем описание категории
            repository.save(existingCategory);
            return ResponseEntity.ok("Категория обновлена.");
        } else {
            return ResponseEntity.badRequest().body("Категория не найдена.");
        }
    }


    // Метод для удаления категории
    @GetMapping("/{category_name}/delete")
    public ResponseEntity<String> deleteCategory(@PathVariable String category_name) {
        // Поиск существующей категории
        LoreCategory existingCategory = repository.findByName(category_name);

        // Если категория существует, удаляем ее
        if(existingCategory != null){
            // Удаление категории
            repository.delete(existingCategory);
            // Возвращение ответа
            return ResponseEntity.ok("Категория удалена.");
        }else{
            // Вернуть ошибку, если категория не найдена
            return ResponseEntity.badRequest().body("Категория не найдена.");
        }
    }
}
    //Я хотел бы получить ваши рекомендации по тому как правильнее реализовать данный способ для создания категорий и управления топиками
    //Я решил создать довольно интересную задумку, чтобы модератор сам решал какие категории ему нужны в лоре, так как это дело каждого
    //Так что здесь я не создаю чёткого ограничения, и модератор вправе сам создавать новые категории, топики в них и в них уже создавать какие либо сущности
    //В качестве примера модератор создаёт категорию "Боги" и в ней создаёт топик "Бог войны", "Богиня магии" и т.д.