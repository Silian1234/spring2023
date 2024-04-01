package com.example.spring2023.controllers;

import com.example.spring2023.models.Feats;
import com.example.spring2023.repo.FeatsRepository;
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
public class FeatsController {

    @Autowired
    private FeatsRepository featsRepository;

    @GetMapping("/feats")
    public String listFeats(Model model) {
        Iterable<Feats> feats = featsRepository.findAll();
        model.addAttribute("feats", featsRepository.findAll());
        return "feats"; // Возвращает страницу со списком черт
    }

    @GetMapping("/feats/add")
    public String featsAdd(Model model) {
        return "feats-add";
    }

    @GetMapping("/feats/{id}")
    public String featsInfo(@PathVariable(value = "id") long id, Model model) {
        Optional<Feats> feat = featsRepository.findById(id);
        ArrayList<Feats> result = new ArrayList<>();
        feat.ifPresent(result::add);
        model.addAttribute("feat", result);
        return "feats-info";
    }

    @GetMapping("/feats/{id}/edit")
    public String featsEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<Feats> feat = featsRepository.findById(id);
        ArrayList<Feats> result = new ArrayList<>();
        feat.ifPresent(result::add);
        model.addAttribute("feats", result);
        return "feats-edit";
    }

    @PostMapping("/feats/{id}/edit")
    public String featsUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String benefit, @RequestParam String description, @RequestParam String prerequisite, @RequestParam String source, Model model) {
        Optional<Feats> feat = featsRepository.findById(id);
        if (feat.isPresent()) {
            Feats updatedFeat = feat.get();
            updatedFeat.setName(name);
            updatedFeat.setBenefit(benefit);
            updatedFeat.setDescription(description);
            updatedFeat.setPrerequisite(prerequisite);
            updatedFeat.setSource(source);
            featsRepository.save(updatedFeat);
        }
        return "redirect:/feats";
    }

    @PostMapping("/feats/add")
    public String featsPostAdd(@RequestParam String name, @RequestParam String benefit, @RequestParam String description, @RequestParam String prerequisite, @RequestParam String source, Model model) {
        Feats feats = new Feats(name, benefit, description, prerequisite, source);
        featsRepository.save(feats);
        return "redirect:/feats";
    }

}
