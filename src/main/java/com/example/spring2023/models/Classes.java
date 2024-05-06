package com.example.spring2023.models;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String benefit;

    @ElementCollection
    @CollectionTable(name = "class_abilities")
    @MapKeyColumn(name = "ability_name")
    @Column(name = "ability_description")
    private Map<String, String> abilities = new HashMap<>();

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

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public Map<String, String> getAbilities() {
        return abilities;
    }

    public void setAbilities(Map<String, String> abilities) {
        this.abilities = abilities;
    }

    public Classes() {}

    public Classes(String name, String description, String benefit, Map<String, String> abilities) {
        this.name = name;
        this.description = description;
        this.benefit = benefit;
        this.abilities = abilities;
    }
}
