// Импортирование необходимых библиотек и модулей
package com.example.spring2023.controllers;

import com.example.spring2023.models.Feats;
import com.example.spring2023.repo.FeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

// Класс, управляющий HTTP-запросами к "/api/feats"
@RestController
@RequestMapping("/api/feats")
public class FeatsController {

    // Создание репозитория для работы с базой данных feats
    @Autowired
    private FeatsRepository featsRepository;

    // Обработчик GET-запросов к "/api/feats/"
    @GetMapping("/")
    public ResponseEntity<List<Feats>> listFeats() {
        // Получение всех объектов Feats из базы данных
        List<Feats> feats = StreamSupport.stream(featsRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        // Возвращение списка Feats с статусом 200 (OK)
        return new ResponseEntity<>(feats, HttpStatus.OK);
    }

    // Обработчик GET-запросов к "/api/feats/{id}"
    @GetMapping("/{id}")
    public ResponseEntity<Feats> featsInfo(@PathVariable(value = "id") long id) {
        // Поиск Feats в базе данных по id
        Optional<Feats> feat = featsRepository.findById(id);
        // Если такой Feats найден, возвращает его с кодом 200 (OK)
        // Если нет, возвращает код 404 (Not Found)
        return feat.map(feats -> new ResponseEntity<>(feats, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Обработчик PUT-запросов к "/api/feats/{id}"
    @PutMapping("/{id}")
    public ResponseEntity<Feats> featsUpdate(@PathVariable(value = "id") long id, @RequestBody Map<String, String> updates) {
        // Поиск Feats в базе данных по id
        Optional<Feats> feat = featsRepository.findById(id);
        // Если такой Feats найден, обновляет его значения и сохраняет в базе данных
        // В случае успеха возвращает обновленный Feats с кодом 200 (OK)
        // Если нет, возвращает код 404 (Not Found)
        if (feat.isPresent()){
            Feats updatedFeat = feat.get();
            updatedFeat.setName(updates.get("name"));
            updatedFeat.setBenefit(updates.get("benefit"));
            updatedFeat.setDescription(updates.get("description"));
            updatedFeat.setPrerequisite(updates.get("prerequisite"));
            updatedFeat.setSource(updates.get("source"));
            featsRepository.save(updatedFeat);
            return new ResponseEntity<>(updatedFeat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Обработчик POST-запросов к "/api/feats/"
    @PostMapping("/")
    public ResponseEntity<Feats> featsPostAdd(@RequestBody Map<String, String> newFeatData) {
        // Создание нового объекта Feats с переданными данными и его сохранение в базе данных
        Feats feats = new Feats(newFeatData.get("name"), newFeatData.get("benefit"), newFeatData.get("description"), newFeatData.get("prerequisite"), newFeatData.get("source"));
        featsRepository.save(feats);
        // Возвращение созданного объекта с кодом 201 (Created)
        return new ResponseEntity<>(feats, HttpStatus.CREATED);
    }
}