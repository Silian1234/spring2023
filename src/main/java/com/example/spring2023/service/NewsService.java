package com.example.spring2023.service;

import com.example.spring2023.models.News;
import com.example.spring2023.repo.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Компонент сервиса
public class NewsService {

    @Autowired // Внедрение зависимости репозитория
    private NewsRepository newsRepository;

    public List<News> getAllNews() { // Получить все новости
        return newsRepository.findAll();
    }

    public News getNewsById(Long id) { // Получить новость по ID
        return newsRepository.findById(id).orElse(null);
    }

    public News saveNews(News news) { // Сохранить новость
        return newsRepository.save(news);
    }

    public void deleteNews(Long id) { // Удалить новость по ID
        newsRepository.deleteById(id);
    }
}