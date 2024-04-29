package com.example.spring2023.controllers;

import com.example.spring2023.models.LoreCategory;
import com.example.spring2023.models.LoreTopic;
import com.example.spring2023.repo.LoreCategoryRepository;
import com.example.spring2023.repo.LoreTopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoreTopicController.class)
class LoreTopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoreTopicRepository topicRepository;

    @MockBean
    private LoreCategoryRepository categoryRepository;

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void testCreateTopic() throws Exception {
        LoreCategory category = new LoreCategory();
        category.setId(1L);

        String topicJson = "{\"title\":\"Test Topic\", \"content\":\"Test content\"}";

        given(categoryRepository.findById(any())).willReturn(Optional.of(category));

        mockMvc.perform(post("/lore/1/topics")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Топик успешно создан."));

        verify(topicRepository).save(any(LoreTopic.class));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void testUpdateTopicNotFound() throws Exception {
        String updatedTopicJson = "{\"title\":\"Updated Title\", \"content\":\"Updated content\"}";

        given(categoryRepository.existsById(any())).willReturn(true);
        given(topicRepository.findById(any())).willReturn(Optional.empty());

        mockMvc.perform(put("/lore/1/topics/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTopicJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Топик не найден."));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void testDeleteTopicSuccess() throws Exception {
        LoreTopic topic = new LoreTopic();
        topic.setId(1L);

        given(categoryRepository.existsById(any())).willReturn(true);
        given(topicRepository.findById(any())).willReturn(Optional.of(topic));

        mockMvc.perform(delete("/lore/1/topics/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Топик успешно удален."));


        verify(topicRepository).delete(any(LoreTopic.class));
    }
}
