package com.enigmacamp.springbootdatapenduduk;

import com.enigmacamp.springbootdatapenduduk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private final UserService userService;
    @Override
    public void run(String... args) throws Exception {
        userService.createAdminIfNotExists();
        System.out.println("Admin initialized successfully!");
    }
}
