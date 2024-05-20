package com.example.spring2023.controllers;

import com.example.spring2023.config.WebSecurityConfig;
import com.example.spring2023.models.Ability;
import com.example.spring2023.models.Ability.Category;
import com.example.spring2023.models.Classes;
import com.example.spring2023.repo.ClassesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClassesController.class)
@Import(WebSecurityConfig.class)
public class ClassesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassesRepository classesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Classes class1;
    private Classes class2;

    @BeforeEach
    void setUp() {
        Ability ability1 = new Ability("Ability1", "Description1", Category.CLASS);
        Ability ability2 = new Ability("Ability2", "Description2", Category.CLASS);

        class1 = new Classes("Class1", "Description1", "Benefit1", Arrays.asList(ability1));
        class2 = new Classes("Class2", "Description2", "Benefit2", Arrays.asList(ability2));
    }

    @Test
    @WithMockUser
    public void testListClasses() throws Exception {
        when(classesRepository.findAll()).thenReturn(Arrays.asList(class1, class2));

        mockMvc.perform(get("/api/classes/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Class1"))
                .andExpect(jsonPath("$[1].name").value("Class2"));
    }

    @Test
    @WithMockUser
    public void testClassesInfo() throws Exception {
        when(classesRepository.findById(1L)).thenReturn(Optional.of(class1));

        mockMvc.perform(get("/api/classes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Class1"));
    }

    @Test
    @WithMockUser
    public void testClassesAdd() throws Exception {
        when(classesRepository.save(any(Classes.class))).thenReturn(class1);

        mockMvc.perform(post("/api/classes/")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(class1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Class1"));
    }

    @Test
    @WithMockUser
    public void testClassesUpdate() throws Exception {
        when(classesRepository.findById(1L)).thenReturn(Optional.of(class1));
        when(classesRepository.save(any(Classes.class))).thenReturn(class1);

        Classes updatedClass = new Classes("UpdatedClass", "Updated Description", "Updated Benefit", class1.getAbilities());

        mockMvc.perform(put("/api/classes/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedClass)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedClass"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    @WithMockUser
    public void testClassesDelete() throws Exception {
        when(classesRepository.existsById(1L)).thenReturn(true);
        doNothing().when(classesRepository).deleteById(1L);

        mockMvc.perform(delete("/api/classes/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        verify(classesRepository, times(1)).deleteById(1L);
    }
}
