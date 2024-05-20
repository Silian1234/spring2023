package com.example.spring2023.controllers;

import com.example.spring2023.models.Ability;
import com.example.spring2023.models.Classes;
import com.example.spring2023.repo.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/classes")
public class ClassesController {

    @Autowired
    private ClassesRepository classesRepository;

    @GetMapping("/")
    public ResponseEntity<List<Classes>> listClasses() {
        List<Classes> classes = StreamSupport.stream(classesRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classes> classesInfo(@PathVariable(value = "id") long id) {
        Optional<Classes> cl = classesRepository.findById(id);
        return cl.map(classes -> new ResponseEntity<>(classes, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classes> classesUpdate(@PathVariable(value = "id") long id, @RequestBody Classes updatedClassData) {
        Optional<Classes> cl = classesRepository.findById(id);
        if(cl.isPresent()){
            Classes updatedClass = cl.get();
            updatedClass.setName(updatedClassData.getName());
            updatedClass.setDescription(updatedClassData.getDescription());
            updatedClass.setBenefit(updatedClassData.getBenefit());
            updatedClass.setAbilities(updatedClassData.getAbilities());
            updatedClass.setSource(updatedClassData.getSource());
            classesRepository.save(updatedClass);
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Classes> classesPostAdd(@RequestBody Classes newClassData) {
        classesRepository.save(newClassData);
        return new ResponseEntity<>(newClassData, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> classesDelete(@PathVariable(value = "id") long id) {
        if (classesRepository.existsById(id)) {
            classesRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}