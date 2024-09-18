package com.enigmacamp.springbootdatapenduduk.config;

import com.enigmacamp.springbootdatapenduduk.model.enums.Role;
import com.enigmacamp.springbootdatapenduduk.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Menggunakan lambda untuk mengkonfigurasi csrf
        return http
                // versi terbaru setelah 6.1
                .csrf(AbstractHttpConfigurer::disable) // Nonaktifkan CSRF untuk API berbasis token
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Izinkan akses tanpa autentikasi
                        .requestMatchers(HttpMethod.POST, "/api/v1/provinces").hasRole(String.valueOf(Role.ADMIN))
                        .anyRequest().permitAll() // Semua permintaan lain membutuhkan autentikasi
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Konfigurasi sesi stateless untuk JWT
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Tambahkan filter JWT
                .build();
    }
}
