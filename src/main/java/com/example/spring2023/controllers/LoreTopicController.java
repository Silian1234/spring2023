package com.example.spring2023.controllers;

//импорт моделей приложения
import com.example.spring2023.models.LoreCategory;
import com.example.spring2023.models.LoreTopic;
//импорт репозиториев для взаимодействия с базой данных
import com.example.spring2023.repo.LoreCategoryRepository;
import com.example.spring2023.repo.LoreTopicRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Декларация класса как REST контроллера
@RestController
//Сопоставление URL, например: "/api/lore/{categoryId}/topics"
@RequestMapping("/api/lore/{categoryId}/topics")

public class LoreTopicController {

    //Объявление репозиториев для работы с базой данных
    private final LoreTopicRepository topicRepository;
    private final LoreCategoryRepository categoryRepository;

    //Инициализация репозиториев с помощью конструктора
    public LoreTopicController(LoreTopicRepository topicRepository, LoreCategoryRepository categoryRepository) {
        this.topicRepository = topicRepository;
        this.categoryRepository = categoryRepository;
    }

    //Метод для получения всех тем в заданной категории
    @GetMapping("/")
    public ResponseEntity<List<LoreTopic>> getAllTopics(@PathVariable Long categoryId) {
        Optional<LoreCategory> categoryOpt = categoryRepository.findById(categoryId);

        //Проверка на существование категории
        if (!categoryOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        //Получение список тем
        List<LoreTopic> topics = topicRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(topics);
    }

    //Метод для получения темы по id в определенной категории
    @GetMapping("/{topicId}")
    public ResponseEntity<LoreTopic> getTopicById(@PathVariable Long categoryId, @PathVariable Long topicId) {
        Optional<LoreCategory> categoryOpt = categoryRepository.findById(categoryId);

        if (!categoryOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<LoreTopic> topicOpt = topicRepository.findById(topicId);

        if (!topicOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        //Возвращаем найденную тему
        return ResponseEntity.ok(topicOpt.get());
    }

    //Метод для создания новой темы
    @PostMapping("/")
    public ResponseEntity<LoreTopic> createTopic(@PathVariable Long categoryId, @RequestBody LoreTopic topic) {
        Optional<LoreCategory> categoryOpt = categoryRepository.findById(categoryId);

        if (!categoryOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        //Установка категории для новой темы
        LoreCategory category = categoryOpt.get();
        topic.setCategory(category);
        //Сохранение новой темы
        topicRepository.save(topic);
        return ResponseEntity.ok(topic);
    }

    //Метод для обновления существующей темы
    @PutMapping("/{topicId}")
    public ResponseEntity<LoreTopic> updateTopic(@PathVariable Long categoryId, @PathVariable Long topicId, @RequestBody LoreTopic updatedTopic) {
        Optional<LoreCategory> categoryOpt = categoryRepository.findById(categoryId);

        if (!categoryOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<LoreTopic> topicOpt = topicRepository.findById(topicId);

        if (!topicOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        //Перезапись информации о теме
        LoreTopic topic = topicOpt.get();
        topic.setTitle(updatedTopic.getTitle());
        topic.setContent(updatedTopic.getContent());
        //Сохранение обновленной темы
        topicRepository.save(topic);
        return ResponseEntity.ok(topic);
    }

    //Метод для удаления темы
    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long categoryId, @PathVariable Long topicId) {
        Optional<LoreCategory> categoryOpt = categoryRepository.findById(categoryId);

        if (!categoryOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<LoreTopic> topicOpt = topicRepository.findById(topicId);

        if (!topicOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        //Удаление темы
        topicRepository.deleteById(topicId);
        return ResponseEntity.ok().build();
    }
}