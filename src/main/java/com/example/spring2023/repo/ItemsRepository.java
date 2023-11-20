package com.example.spring2023.repo;

import com.example.spring2023.models.Items;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends CrudRepository<Items, Long> {

}
