package com.example.spring2023.controllers;

import com.example.spring2023.models.News;
import com.example.spring2023.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Контроллер для обработки HTTP-запросов
@RequestMapping("/news") // Базовый URL для методов контроллера
public class NewsController {

    @Autowired // Внедрение зависимости сервиса
    private NewsService newsService;

    @GetMapping // Получение списка всех новостей
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/{id}") // Получение новости по ID
    public News getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }

    @PostMapping // Создание новой новости
    public News postNews(@RequestBody News news) {
        return newsService.saveNews(news);
    }

    @DeleteMapping("/{id}") // Удаление новости по ID
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
