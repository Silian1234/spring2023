package com.example.spring2023.controllers;

// Импортируем необходимые классы и библиотеки
import com.example.spring2023.models.Classes;
import com.example.spring2023.repo.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController  // Аннотация, указывающая, что этот класс является REST контроллером
@RequestMapping("/api/classes")  // Аннотация для маппинга URL
public class ClassesController {

    @Autowired  // Аннотация для автоматического связывания экземпляров в Spring
    private ClassesRepository classesRepository;

    // Эндпоинт для получения списка всех классов
    @GetMapping("/")
    public ResponseEntity<List<Classes>> listClasses() {
        List<Classes> classes =
StreamSupport.stream(classesRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());  // Использование Stream API для создания списка классов
        // Возврат списка классов в виде HTTP-ответа с статусом ОК
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    // Эндпоинт для получения информации о конкретном классе по его id
    @GetMapping("/{id}")
    public ResponseEntity<Classes> classesInfo(@PathVariable(value = "id") long id) {
        Optional<Classes> cl = classesRepository.findById(id);  // Поиск класса по ID
        // Проверяем, найден ли класс. Если да, то возвращаем его. Если нет, то возвращаем статус NOT_FOUND
        return cl.map(classes -> new ResponseEntity<>(classes, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Эндпоинт для обновления класса по его id
    @PutMapping("/{id}")
    public ResponseEntity<Classes> classesUpdate(@PathVariable(value = "id") long id, @RequestBody Map<String, String> updates) {
        Optional<Classes> cl = classesRepository.findById(id);  // Поиск класса по ID
        if(cl.isPresent()){
            Classes updatedClass = cl.get();  // Получаем класс из Optional
            // Обновляем поля класса
            updatedClass.setName(updates.get("name"));
            updatedClass.setDescription(updates.get("description"));
            updatedClass.setBenefit(updates.get("benefit"));
            updatedClass.setAbilities(updates);
            classesRepository.save(updatedClass);  // Сохраняем обновленный класс
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);  // Возвращаем обновленный класс
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Если класс не найден, то возвращаем статус NOT_FOUND
        }
    }

    // Эндпоинт для создания нового класса
    @PostMapping("/")
    public ResponseEntity<Classes> classesPostAdd(@RequestBody Map<String, String> newClassData) {
        // Создаем новый класс и передаем в него данные
        Classes newClass = new Classes(newClassData.get("name"), newClassData.get("description"), newClassData.get("benefit"), newClassData);
        classesRepository.save(newClass);  // Сохраняем новый класс
        // Возвращаем новый класс с HTTP статусом CREATED
        return new ResponseEntity<>(newClass, HttpStatus.CREATED);
    }
}