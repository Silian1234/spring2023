package com.example.spring2023.controllers;

import com.example.spring2023.models.Items;
import com.example.spring2023.repo.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

// Аннотация RestController означает, что данный класс рассматривается как контроллер,
// который будет отвечать на запросы к Web API.
@RestController
@RequestMapping("/api") // Пометка URL-пути (точка входа в API), который будет обрабатываться этим контроллером.
public class ItemsController {

    @Autowired // Автоматическое связывание нашего репозитория Spring
    private ItemsRepository itemsRepository; // Репозиторий для работы с БД и объектами Items

    // Метод, отвечающий на GET-запросы по пути "/items" и возвращающий все Items из БД
    @GetMapping("/items")
    public ResponseEntity<List<Items>> getItems() {
        Iterable<Items> itemsIterable = itemsRepository.findAll(); // Получаем все элементы из БД
        List<Items> itemsList = StreamSupport // Преобразуем итерируемую коллекцию в список
                .stream(itemsIterable.spliterator(), false)
                .collect(Collectors.toList());

        return ResponseEntity.ok(itemsList); // Возвращаем список со статусом HTTP 200
    }

    // Метод, отвечающий на GET-запросы по пути "/items/{id}" и возвращающий Items с заданным id (если таковой имеется)
    @GetMapping("/items/{id}")
    public ResponseEntity<List<Items>> getItem(@PathVariable(value = "id") long id) {
        Optional<Items> item = itemsRepository.findById(id); // Находим элемент с заданным id
        ArrayList<Items> result = new ArrayList<>();
        item.ifPresent(result::add); // Если элемент найден, добавляем его в result

        return item.isPresent() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build(); // Если элемент не найден, возвращаем 404
    }

    // Метод, отвечающий на PUT-запросы по пути "/items/{id}", обновляет элемент с заданным id (если он существует) и возвращает обновлённый элемент
    @PutMapping("/items/{id}")
    public ResponseEntity<Items> updateItem(@PathVariable(value = "id") long id, @RequestBody Items updatedItemInfo) {
        Optional<Items> item = itemsRepository.findById(id); // Находим элемент с заданным id
        if (item.isPresent()) {
            Items updatedItem = item.get(); // Получаем найденный элемент
            updatedItem.setName(updatedItemInfo.getName()); // Обновляем поля объекта новыми значениями
            updatedItem.setRarity(updatedItemInfo.getRarity());
            updatedItem.setDescription(updatedItemInfo.getDescription());
            updatedItem.setType(updatedItemInfo.getType());
            updatedItem.setSource(updatedItemInfo.getSource());
            return ResponseEntity.ok(itemsRepository.save(updatedItem)); // Сохраняем и возвращаем обновлённый объект со статусом HTTP 200
        }
        return ResponseEntity.notFound().build(); // Если элемент не найден, возвращаем 404
    }

    // Метод, отвечающий на POST-запросы по пути "/items", создаёт новый элемент Items и добавляет его в базу данных
    @PostMapping("/items")
    public ResponseEntity<Items> addItem(@RequestBody Items newItem) {
        Items savedItem = itemsRepository.save(newItem); // Сохраняем новый элемент в БД
        return ResponseEntity.ok(savedItem); // Возвращаем сохранённый элемент со статусом HTTP 200
    }
}