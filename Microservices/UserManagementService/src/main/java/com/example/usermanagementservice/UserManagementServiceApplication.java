package com.example.usermanagementservice;

import com.example.usermanagementservice.domain.Settings;
import com.example.usermanagementservice.domain.User;
import com.example.usermanagementservice.repository.SettingsRepository;
import com.example.usermanagementservice.repository.UserRepository;
import com.example.usermanagementservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, SettingsRepository settingsRepository) {
        return args -> {
            Settings settings1 =  settingsRepository.save(new Settings(true, true));
            Settings settings2 =  settingsRepository.save(new Settings(false, false));
            userRepository.save(new User("kb", "bill", "kill", "kill bill", settings1));
            userRepository.save(new User("ant", "antonio", "banderas", "bruh", settings2));
        };
    }
}
