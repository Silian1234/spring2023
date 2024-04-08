package com.example.spring2023.repo;

import com.example.spring2023.models.Races;
import org.springframework.data.repository.CrudRepository;

public interface RacesRepository extends CrudRepository<Races, Long> {

}
