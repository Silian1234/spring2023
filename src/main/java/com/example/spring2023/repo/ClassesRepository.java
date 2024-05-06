package com.example.spring2023.repo;

import com.example.spring2023.models.Classes;
import org.springframework.data.repository.CrudRepository;

public interface ClassesRepository extends CrudRepository<Classes, Long> {
}
