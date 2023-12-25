package com.example.spring2023.controllers;

import com.example.spring2023.models.Items;
import com.example.spring2023.models.Users;
import com.example.spring2023.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String news(Model model) {
        model.addAttribute("title", "Профиль");
        return "profileDir/profile";
    }

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/registration")
    public String itemsAdd(Model model) {
        return "profileDir/profileReg";
    }

    @PostMapping("/registration")
    public String usersPostAdd(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam(required = false) String type, Model model) {
        Users user = new Users(name, email, password, type);
        user.setUserType("user");
        usersRepository.save(user);
        return "redirect:";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Имя вашего шаблона страницы входа
    }

}
