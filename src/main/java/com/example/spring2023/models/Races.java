package com.example.spring2023.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import java.util.Set;

@Entity
public class Races {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String features;
    private String appearance;
    private String benefit;

    private String source;

    @ElementCollection
    private Set<String> tags; // Метки для фильтра

    public Races(Long id, String name, String description, String features, String appearance, String benefit, String source, Set<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.features = features;
        this.appearance = appearance;
        this.benefit = benefit;
        this.source = source;
        this.tags = tags;
    }

    public Races() {

    }

    // Геттеры и сеттеры

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getAdvantages() {
        return appearance;
    }

    public void setAdvantages(String advantages) {
        this.appearance = advantages;
    }

    public String getDisadvantages() {
        return benefit;
    }

    public void setDisadvantages(String disadvantages) {
        this.benefit = disadvantages;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
