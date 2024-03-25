package com.example.spring2023.controllers;

import com.example.spring2023.models.Items;
import com.example.spring2023.models.Users;
import com.example.spring2023.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Console;

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
    public String usersAdd(Model model) {
        return "profileDir/profileReg";
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public String usersPostAdd(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam(required = false) String type, Model model) {
        String encodedPassword = passwordEncoder.encode(password);
        Users user = new Users(name, email, encodedPassword, type);
        user.setUserType("user");
        usersRepository.save(user);
        return "redirect:/login"; // Перенаправление на страницу входа после успешной регистрации
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Имя вашего шаблона страницы входа
    }

}
