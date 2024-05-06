package com.example.spring2023.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring2023.models.Classes;
import com.example.spring2023.repo.ClassesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClassesController.class)
public class ClassesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassesRepository classesRepository;

    @Test
    @WithMockUser
    void listClasses() throws Exception {
        Classes testClass1 = new Classes("Class1", "Description1", "Benefit1", null);
        Classes testClass2 = new Classes("Class2", "Description2", "Benefit2", null);
        when(classesRepository.findAll()).thenReturn(Arrays.asList(testClass1, testClass2));

        mockMvc.perform(get("/classes")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @WithMockUser
    void classesAdd() throws Exception {
        mockMvc.perform(get("/classes/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("classes-add")); // Проверяем, что возвращаемое имя представления соответствует ожидаемому
    }

    @Test
    @WithMockUser
    void classesInfo() throws Exception {
        Classes testClass = new Classes("TestClass", "Test Description", "Test Benefit", null);
        when(classesRepository.findById(1L)).thenReturn(Optional.of(testClass));

        mockMvc.perform(get("/classes/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void classesEdit() throws Exception {
        Classes testClass = new Classes("TestClass", "Test Description", "Test Benefit", null);
        when(classesRepository.findById(1L)).thenReturn(Optional.of(testClass));

        mockMvc.perform(get("/classes/{id}/edit", 1)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void classesUpdate() throws Exception {
        Classes existingClass = new Classes("ExistingClass", "Existing Description", "Existing Benefit", null);
        existingClass.setId(1L);

        when(classesRepository.findById(1L)).thenReturn(Optional.of(existingClass));
        when(classesRepository.save(any(Classes.class))).thenReturn(existingClass);

        mockMvc.perform(post("/classes/{id}/edit", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(existingClass))
                        .param("name", "UpdatedName")
                        .param("description", "UpdatedDescription")
                        .param("benefit", "UpdatedBenefit")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void classesPostAdd() throws Exception {
        Classes newClass = new Classes("NewClass", "New Description", "New Benefit", null);

        when(classesRepository.save(Mockito.any(Classes.class))).thenReturn(newClass);

        mockMvc.perform(post("/classes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newClass))
                        .param("name", newClass.getName())
                        .param("description", newClass.getDescription())
                        .param("benefit", newClass.getBenefit())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
