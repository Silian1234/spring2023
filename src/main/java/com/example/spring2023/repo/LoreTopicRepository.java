package com.example.spring2023.repo;

import com.example.spring2023.models.LoreTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoreTopicRepository extends JpaRepository<LoreTopic, Long> {
    List<LoreTopic> findByCategoryId(Long categoryId);
}