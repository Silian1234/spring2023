package com.example.spring2023.repo;

import com.example.spring2023.models.Feats;
import org.springframework.data.repository.CrudRepository;

public interface FeatsRepository extends CrudRepository<Feats, Long> {
}
