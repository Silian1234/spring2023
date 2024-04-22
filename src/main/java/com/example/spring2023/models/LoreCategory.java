package com.example.spring2023.models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class LoreCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category")
    private Set<LoreTopic> topics;

    public LoreCategory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LoreTopic> getTopics() {
        return topics;
    }

    public void setTopics(Set<LoreTopic> topics) {
        this.topics = topics;
    }

    public LoreCategory(Long id, String name, Set<LoreTopic> topics) {
        this.id = id;
        this.name = name;
        this.topics = topics;
    }
}