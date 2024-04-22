package com.example.spring2023.models;

import jakarta.persistence.*;

@Entity
public class LoreTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private LoreCategory category;

    public LoreTopic() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LoreCategory getCategory() {
        return category;
    }

    public void setCategory(LoreCategory category) {
        this.category = category;
    }

    public LoreTopic(Long id, String title, String content, LoreCategory category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }
}