package com.example.spring2023.controllers;

import com.example.spring2023.models.Races;
import com.example.spring2023.repo.RacesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RacesController.class)
public class RacesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RacesRepository racesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Races race1;
    private Races race2;

    @BeforeEach
    void setUp() {
        Set<String> tags1 = new HashSet<>(Arrays.asList("Tag1", "Tag2"));
        Set<String> tags2 = new HashSet<>(Arrays.asList("Tag3", "Tag4"));

        race1 = new Races(1L, "Race1", "Description1", "Features1", "Appearance1", "Benefit1", "Source1", tags1);
        race2 = new Races(2L, "Race2", "Description2", "Features2", "Appearance2", "Benefit2", "Source2", tags2);
    }

    @WithMockUser
    @Test
    public void testListRaces() throws Exception {
        when(racesRepository.findAll()).thenReturn(Arrays.asList(race1, race2));

        mockMvc.perform(get("/api/Races/races"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Race1"))
                .andExpect(jsonPath("$[1].name").value("Race2"));
    }

    @WithMockUser
    @Test
    public void testRacesInfo() throws Exception {
        when(racesRepository.findById(1L)).thenReturn(Optional.of(race1));

        mockMvc.perform(get("/api/Races/races/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Race1"));
    }

    @WithMockUser
    @Test
    public void testRacesAdd() throws Exception {
        when(racesRepository.save(any(Races.class))).thenReturn(race1);

        mockMvc.perform(post("/api/Races/races")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(race1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Race1"));
    }

    @WithMockUser
    @Test
    public void testRacesUpdate() throws Exception {
        when(racesRepository.findById(1L)).thenReturn(Optional.of(race1));
        when(racesRepository.save(any(Races.class))).thenReturn(race1);

        Races updatedRace = new Races(1L, "UpdatedRace", "Updated Description", "Updated Features", "Updated Appearance", "Updated Benefit", "Updated Source", new HashSet<>(Arrays.asList("UpdatedTag1", "UpdatedTag2")));

        mockMvc.perform(put("/api/Races/races/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRace)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedRace"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @WithMockUser
    @Test
    public void testRacesDelete() throws Exception {
        doNothing().when(racesRepository).deleteById(1L);

        mockMvc.perform(delete("/api/Races/races/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        verify(racesRepository, times(1)).deleteById(1L);
    }
}
