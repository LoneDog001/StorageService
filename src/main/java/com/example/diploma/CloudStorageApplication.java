package com.example.diploma;

import com.example.diploma.entities.User;
import com.example.diploma.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CloudStorageApplication {

    public static void main(String[] args) {

        SpringApplication.run(CloudStorageApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder) {
        return args -> users.save(new User("user", encoder.encode("password"), "ROLE_USER"));
    }
}
