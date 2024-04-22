package com.example.spring2023.controllers;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import com.example.spring2023.models.LoreCategory;
import com.example.spring2023.repo.LoreCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.HttpSecurityDsl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoreCategoryController.class)
public class LoreCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoreCategoryRepository repository;

    @Test
    @WithMockUser
    public void testAddCategory() throws Exception {
        LoreCategory category = new LoreCategory();
        category.setName("TestCategory");
        when(repository.findByName(anyString())).thenReturn(null);
        when(repository.save(any(LoreCategory.class))).thenReturn(category);

        mockMvc.perform(post("/lore/TestCategory")
                        .with(csrf()) // Добавление CSRF токена
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(category)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void testUpdateCategory() throws Exception {
        LoreCategory existingCategory = new LoreCategory();
        existingCategory.setName("ExistingCategory");

        LoreCategory updatedCategory = new LoreCategory();
        updatedCategory.setName("UpdatedCategory");

        when(repository.findByName("ExistingCategory")).thenReturn(existingCategory);
        when(repository.save(any(LoreCategory.class))).thenReturn(updatedCategory);

        mockMvc.perform(post("/lore/ExistingCategory/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCategory)))
                .andExpect(status().isOk())
                .andExpect(content().string("Категория обновлена."));
    }

    @Test
    @WithMockUser
    public void testDeleteCategory() throws Exception {
        LoreCategory existingCategory = new LoreCategory();
        existingCategory.setName("ExistingCategory");

        when(repository.findByName("ExistingCategory")).thenReturn(existingCategory);

        mockMvc.perform(get("/lore/ExistingCategory/delete"))
                .andExpect(status().isOk())
                .andExpect(content().string("Категория удалена."));

        verify(repository, times(1)).delete(existingCategory);
    }
}