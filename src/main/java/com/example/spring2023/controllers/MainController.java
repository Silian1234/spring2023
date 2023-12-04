package com.example.spring2023.controllers;

import com.example.spring2023.models.Items;
import com.example.spring2023.repo.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/classes")
    public String classes(Model model) {
        model.addAttribute("title", "Классы");
        return "classes";
    }
    @GetMapping("/races")
    public String races(Model model) {
        model.addAttribute("title", "Расы");
        return "races";
    }
    @GetMapping("/feats")
    public String feats(Model model) {
        model.addAttribute("title", "Черты");
        return "feats";
    }

    @Autowired
    private ItemsRepository itemsRepository;

    @GetMapping("/items")
    public String items(Model model) {
        Iterable<Items> items = itemsRepository.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/items/add")
    public String itemsAdd(Model model) {
        return "items-add";
    }

    @GetMapping("/items/{id}")
    public String itemsInfo(@PathVariable(value = "id") long id, Model model) {
        Optional<Items> item = itemsRepository.findById(id);
        ArrayList<Items> result = new ArrayList<>();
        item.ifPresent(result::add);
        model.addAttribute("item", result);
        return "items-info";
    }

    @GetMapping("/items/{id}/edit")
    public String itemsEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<Items> item = itemsRepository.findById(id);
        ArrayList<Items> result = new ArrayList<>();
        item.ifPresent(result::add);
        model.addAttribute("item", result);
        return "items-edit";
    }

    @PostMapping("/items/{id}/edit")
    public String itemsUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String rarity, @RequestParam String description, @RequestParam String type, @RequestParam String source, Model model) {
        Optional<Items> item = itemsRepository.findById(id);
        if (item.isPresent()) {
            Items updatedItem = item.get();
            updatedItem.setName(name);
            updatedItem.setRarity(rarity);
            updatedItem.setDescription(description);
            updatedItem.setType(type);
            updatedItem.setSource(source);
            itemsRepository.save(updatedItem);
        }
        return "redirect:/items";
    }

    @PostMapping("/items/add")
    public String itemsPostAdd(@RequestParam String name, @RequestParam String rarity, @RequestParam String description, @RequestParam String type, @RequestParam String source, Model model) {
        Items items = new Items(name, rarity, description, type, source);
        itemsRepository.save(items);
        return "redirect:/items";
    }
}
