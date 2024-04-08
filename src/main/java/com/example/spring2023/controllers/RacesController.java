package com.example.spring2023.controllers;

import com.example.spring2023.models.Races;
import com.example.spring2023.repo.RacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Controller
public class RacesController {

    @Autowired
    private RacesRepository racesRepository;

    // Получение списка всех рас
    @GetMapping("/races")
    public String listRaces(Model model) {
        Iterable<Races> races = racesRepository.findAll();
        model.addAttribute("races", races);
        return "races"; // Возвращает страницу со списком рас
    }

    // Добавление новой расы
    @GetMapping("/races/add")
    public String racesAdd(Model model) {
        return "races-add";
    }

    // Получение информации о конкретной расе
    @GetMapping("/races/{id}")
    public String racesInfo(@PathVariable(value = "id") long id, Model model) {
        Optional<Races> race = racesRepository.findById(id);
        ArrayList<Races> result = new ArrayList<>();
        race.ifPresent(result::add);
        model.addAttribute("race", result);
        return "races-info";
    }

    // Редактирование информации о расе
    @GetMapping("/races/{id}/edit")
    public String racesEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<Races> race = racesRepository.findById(id);
        ArrayList<Races> result = new ArrayList<>();
        race.ifPresent(result::add);
        model.addAttribute("race", result);
        return "races-edit";
    }

    // Обновление информации о расе
    @PostMapping("/races/{id}/edit")
    public String racesUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String description, @RequestParam String features, @RequestParam String appearance, @RequestParam String benefit, @RequestParam String source, @RequestParam Set<String> tags, Model model) {
        Optional<Races> race = racesRepository.findById(id);
        if (race.isPresent()) {
            Races updatedRace = race.get();
            updatedRace.setName(name);
            updatedRace.setDescription(description);
            updatedRace.setFeatures(features);
            updatedRace.setAdvantages(appearance); // Предполагается, что это должно быть setAppearance
            updatedRace.setDisadvantages(benefit); // Предполагается, что это должно быть setBenefit
            updatedRace.setSource(source);
            updatedRace.setTags(tags);
            racesRepository.save(updatedRace);
        }
        return "redirect:/races";
    }

    // Добавление новой расы через форму
    @PostMapping("/races/add")
    public String racesPostAdd(@RequestParam String name, @RequestParam String description, @RequestParam String features, @RequestParam String appearance, @RequestParam String benefit, @RequestParam String source, @RequestParam Set<String> tags, Model model) {
        Races race = new Races(null, name, description, features, appearance, benefit, source, tags);
        racesRepository.save(race);
        return "redirect:/races";
    }
}
