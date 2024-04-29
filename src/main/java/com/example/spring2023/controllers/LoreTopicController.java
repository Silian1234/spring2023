package com.example.spring2023.controllers;

// Импорт нужных классов
import com.example.spring2023.models.LoreCategory;
import com.example.spring2023.models.LoreTopic;
import com.example.spring2023.repo.LoreCategoryRepository;
import com.example.spring2023.repo.LoreTopicRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Класс контроллера с указанным маршрутом
@Controller
@RequestMapping("/lore/{categoryId}/topics")
public class LoreTopicController {

    // Поля для хранилищ топиков и категорий
    private final LoreTopicRepository topicRepository;
    private final LoreCategoryRepository categoryRepository;

    // Конструктор класса контроллера с инъекцией зависимости через конструктор
    public LoreTopicController(LoreTopicRepository topicRepository, LoreCategoryRepository categoryRepository) {
        this.topicRepository = topicRepository;
        this.categoryRepository = categoryRepository;
    }

    // Метод для создания нового топика в заданной категории
    @PostMapping
    public ResponseEntity<String> createTopic(@PathVariable Long categoryId, @RequestBody LoreTopic topic) {
        // Проверка нахождения категории в базе данных
        return categoryRepository.findById(categoryId).map(category -> {
            // Связывание топика с заданной категорией
            topic.setCategory(category);
            // Сохранение топика в базе данных
            topicRepository.save(topic);
            // Возвращение ответа о успешном создании топика
            return ResponseEntity.ok("Топик успешно создан.");
        }).orElse(ResponseEntity.badRequest().body("Категория не найдена."));
    }

    // Метод для обновления существующего топика
    @PutMapping("/{topicId}")
    public ResponseEntity<String> updateTopic(@PathVariable Long categoryId, @PathVariable Long topicId, @RequestBody LoreTopic updatedTopic) {
        // Проверка нахождения категории в базе данных
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.badRequest().body("Категория не найдена.");
        }
        // Проверка нахождения топика в базе данных
        return topicRepository.findById(topicId).map(topic -> {
            // Обновление данных топика
            topic.setTitle(updatedTopic.getTitle());
            topic.setContent(updatedTopic.getContent());
            // Сохранение обновлённого топика в базе данных
            topicRepository.save(topic);
            // Возвращение ответа об успешном обновлении топика
            return ResponseEntity.ok("Топик успешно обновлен.");
        }).orElse(ResponseEntity.badRequest().body("Топик не найден."));
    }

    // Метод для удаления топика
    @DeleteMapping("/{topicId}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long categoryId, @PathVariable Long topicId) {
        // Проверка нахождения категории в базе данных
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.badRequest().body("Категория не найдена.");
        }
        // Проверка нахождения топика в базе данных
        return topicRepository.findById(topicId).map(topic -> {
            // Удаление топика из базы данных
            topicRepository.delete(topic);
            // Возвращение ответа об успешном удалении топика
            return ResponseEntity.ok("Топик успешно удален.");
        }).orElse(ResponseEntity.badRequest().body("Топик не найден."));
    }
}