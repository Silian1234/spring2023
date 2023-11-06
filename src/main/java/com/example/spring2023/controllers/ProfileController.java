package com.example.spring2023.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String news(Model model) {
        model.addAttribute("title", "Профиль");
        return "profileDir/profile";
    }
}
