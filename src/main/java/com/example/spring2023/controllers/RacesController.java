package com.example.spring2023.controllers;

// Импорт необходимых библиотек и моделей
import com.example.spring2023.models.Races;
import com.example.spring2023.repo.RacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

// Контроллер, обрабатывающий запросы API, связанные с расами
@RestController
@RequestMapping("/api/Races")
public class RacesController {
    @Autowired
    private RacesRepository racesRepository; // Ссылка на репозиторий рас

    // Эндпоинт для получения списка всех рас
    @GetMapping("/races")
    public List<Races> listRaces() {
        // Получаем из репозитория Iterable объект Races
        Iterable<Races> racesIterable = racesRepository.findAll();

        // Преобразуем Iterable в List и возвращаем его
        List<Races> racesList = StreamSupport.stream(racesIterable.spliterator(), false).collect(Collectors.toList());
        return racesList;
    }

    // Эндпоинт для получения информации о конкретной расе по id
    @GetMapping("/races/{id}")
    public Optional<Races> racesInfo(@PathVariable(value = "id") long id) {
        // Возвращаем из репозитория объект Races по id
        return racesRepository.findById(id);
    }

    // Эндпоинт для добавления новой расы
    @PostMapping("/races")
    public Races racesAdd(@RequestBody Races race) {
        // Сохраняем в репозитории новый объект Races и возвращаем его в ответе на запрос
        return racesRepository.save(race);
    }

    // Эндпоинт для обновления информации о расе по id
    @PutMapping("/races/{id}")
    public Optional<Races> racesUpdate(@PathVariable(value = "id") long id, @RequestBody Races newRace) {
        // Ищем в репозитории объект Races по id, обновляем его поля и сохраняем обновленный объект
        return racesRepository.findById(id).map(race -> {
            race.setName(newRace.getName());
            race.setDescription(newRace.getDescription());
            race.setFeatures(newRace.getFeatures());
            race.setSource(newRace.getSource());
            race.setTags(newRace.getTags());
            return racesRepository.save(race);
        });
    }

    // Эндпоинт для удаления расы по id
    @DeleteMapping("/races/{id}")
    public void racesDelete(@PathVariable(value = "id") long id) {
        // Удаляем из репозитория объект Races по id
        racesRepository.deleteById(id);
    }
}