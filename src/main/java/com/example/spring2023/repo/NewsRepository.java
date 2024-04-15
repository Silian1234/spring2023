package com.example.spring2023.repo;

import com.example.spring2023.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    // Интерфейс для репозитория, обеспечивающий CRUD-операции для сущности News
}