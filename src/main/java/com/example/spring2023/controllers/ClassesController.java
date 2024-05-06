package com.example.spring2023.controllers;

import com.example.spring2023.models.Classes;
import com.example.spring2023.repo.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class ClassesController {

    @Autowired
    private ClassesRepository classesRepository;  // Автоматическое подключение репозитория классов

    // Отображение списка всех классов
    @GetMapping("/classes")
    public String listClasses(Model model) {
        Iterable<Classes> classes = classesRepository.findAll();  // Получение всех классов из репозитория
        model.addAttribute("classes", classes);  // Добавление списка классов в модель для отображения
        return "classes";  // Возвращение страницы со списком классов
    }

    // Переход к форме добавления нового класса
    @GetMapping("/classes/add")
    public String classesAdd(Model model) {
        return "classes-add";  // Возвращение страницы для добавления нового класса
    }

    // Получение и отображение деталей конкретного класса
    @GetMapping("/classes/{id}")
    public String classesInfo(@PathVariable(value = "id") long id, Model model) {
        Optional<Classes> cl = classesRepository.findById(id);  // Поиск класса по идентификатору
        ArrayList<Classes> result = new ArrayList<>();
        cl.ifPresent(result::add);  // Добавление найденного класса в список, если он существует
        model.addAttribute("class", result);  // Добавление класса в модель
        return "classes-info";  // Возвращение страницы с информацией о классе
    }

    // Переход к форме редактирования класса
    @GetMapping("/classes/{id}/edit")
    public String classesEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<Classes> cl = classesRepository.findById(id);  // Поиск класса по идентификатору
        ArrayList<Classes> result = new ArrayList<>();
        cl.ifPresent(result::add);  // Добавление найденного класса в список, если он существует
        model.addAttribute("class", result);  // Добавление класса в модель
        return "classes-edit";  // Возвращение страницы для редактирования класса
    }

    // Обновление данных класса после редактирования
    @PostMapping("/classes/{id}/edit")
    public String classesUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String description, @RequestParam String benefit, @RequestParam Map<String, String> abilities, Model model) {
        Optional<Classes> cl = classesRepository.findById(id);  // Поиск класса по идентификатору
        if (cl.isPresent()) {
            Classes updatedClass = cl.get();  // Получение объекта класса для обновления
            updatedClass.setName(name);  // Обновление имени класса
            updatedClass.setDescription(description);  // Обновление описания
            updatedClass.setBenefit(benefit);  // Обновление преимущества
            updatedClass.setAbilities(abilities);  // Обновление списка способностей
            classesRepository.save(updatedClass);  // Сохранение изменений в базе данных
        }
        return "redirect:/classes";  // Перенаправление на страницу со списком классов
    }

    // Добавление нового класса
    @PostMapping("/classes/add")
    public String classesPostAdd(@RequestParam String name, @RequestParam String description, @RequestParam String benefit, @RequestParam Map<String, String> abilities, Model model) {
        Classes newClass = new Classes(name, description, benefit, abilities);  // Создание нового объекта класса
        classesRepository.save(newClass);  // Сохранение нового класса в базе данных
        return "redirect:/classes";  // Перенаправление на страницу со списком классов
    }
}
