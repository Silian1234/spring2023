package com.example.spring2023.controllers;

// Импорт необходимых библиотек и классов
import com.example.spring2023.models.Users;
import com.example.spring2023.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// Контроллер для работы с профилями пользователей
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    // Автоматическое внедрение репозитория пользователей
    @Autowired
    private UsersRepository usersRepository;

    // Автоматическое внедрение кодера паролей
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Обработка POST запроса на регистрацию нового пользователя
     * @param user данные нового пользователя
     * @return данные сохранённого пользователя
     */
    @PostMapping("/registration")
    public Users usersPostAdd(@RequestBody Users user) {
        // Кодирование пароля пользователя
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        // Установка пароля пользователю
        user.setPassword(encodedPassword);
        // Установка типа пользователя
        user.setUserType("user");
        // Сохранение пользователя в репозиторий и возврат его
        return usersRepository.save(user);
    }

    // Обработка GET запроса на получение списка всех пользователей
    @GetMapping("/users")
    public Iterable<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Обработка GET запроса на получение пользователя по его ID
    @GetMapping("/users/{id}")
    public Optional<Users> getUserById(@PathVariable Long id) {
        return usersRepository.findById(id);
    }

    // Обработка PUT запроса на обновление данных пользователя по его ID
    @PutMapping("/users/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody Users userDetails) {
        // Поиск пользователя по ID и исключение, если пользователь не найден
        Users user = usersRepository.findById(id).orElseThrow();
        // Кодирование нового пароля пользователя
        String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
        // Установка нового пароля пользователю
        user.setPassword(encodedPassword);
        // Установка нового имени пользователя
        user.setUsername(userDetails.getUsername());
        // Сохранение обновленного пользователя в репозиторий и возврат его
        user = usersRepository.save(user);
        return user;
    }
}