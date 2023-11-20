package com.example.spring2023.controllers;

import com.example.spring2023.models.Items;
import com.example.spring2023.repo.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
