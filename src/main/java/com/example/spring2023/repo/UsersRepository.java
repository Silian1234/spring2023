package com.example.spring2023.repo;

import com.example.spring2023.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long>{
}
