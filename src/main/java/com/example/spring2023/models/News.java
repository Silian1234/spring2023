package com.example.spring2023.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity // Сущность JPA
public class News {

    @Id // Первичный ключ
    @GeneratedValue(strategy = GenerationType.AUTO) // Автоматическая генерация ID
    private Long id;
    private String title; // Заголовок новости
    private String content; // Содержимое новости
    private LocalDateTime datePublished; // Дата публикации

    // Конструкторы
    public News() {}

    public News(String title, String content, LocalDateTime datePublished) {
        this.title = title;
        this.content = content;
        this.datePublished = datePublished;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getDatePublished() { return datePublished; }
    public void setDatePublished(LocalDateTime datePublished) { this.datePublished = datePublished; }
}

