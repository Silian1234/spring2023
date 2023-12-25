package com.example.spring2023.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class CustomUserDetailsService implements UserDetailsService {

    // Внедрите ваш репозиторий для доступа к данным пользователя

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Загрузите пользователя из базы данных
        // Преобразуйте его в объект CustomUserDetails и верните
        return null;
    }
}