package com.enigmacamp.springbootdatapenduduk.service;

import com.enigmacamp.springbootdatapenduduk.model.dto.request.UserRequestDto;
import com.enigmacamp.springbootdatapenduduk.model.entity.User;
import com.enigmacamp.springbootdatapenduduk.model.enums.Role;
import com.enigmacamp.springbootdatapenduduk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-data-penduduk
 * User: jutioncandrakirana
 * Email: jutionck@gmail.com
 * Telegram : jutionck
 * Date: 11/09/24
 * Time: 09.54
 * To change this template use File | Settings | File Templates.
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Mengambil username dan password dari application.properties
    @Value("${springboot-data-penduduk.admin.username}")
    private String adminUsername;

    @Value("${springboot-data-penduduk.admin.password}")
    private String adminPassword;

    public User registerNewUser(UserRequestDto.Register payload) {
        userRepository.findByUsername(payload.getUsername()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        });
        User newUser = User.builder()
                .username(payload.getUsername())
                .password(passwordEncoder.encode(payload.getPassword()))
                .role(Role.USER)
                .build();
        return userRepository.save(newUser);
    }

    public String login(UserRequestDto.Login payload) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword())
        );
        User user = userRepository.findByUsername(payload.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credential"));
        return jwtService.generateToken(user);
    }

    public void createAdminIfNotExists() {
        userRepository.findByUsername(adminUsername).orElseGet(() -> {
            User admin = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMIN)
                    .build();
            return userRepository.save(admin);
        });
    }
}
