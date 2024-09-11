package com.enigmacamp.springbootdatapenduduk.config;

import com.enigmacamp.springbootdatapenduduk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-data-penduduk
 * User: jutioncandrakirana
 * Email: jutionck@gmail.com
 * Telegram : jutionck
 * Date: 11/09/24
 * Time: 09.46
 * To change this template use File | Settings | File Templates.
 */

@Configuration // menandakan sebagai class configuration dan otomatis di panggil saar app dijalankan
@RequiredArgsConstructor
public class JwtConfiguration {
    private final UserRepository userRepository;

    // @Bean -> adalah object yang diciptakan dari Spring IoC Container.

    @Bean
    public UserDetailsService userDetailsService() { // UserDetailsService bawaan dari spring security
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // AuthenticationProvider -> melakukan pengecekan ke database atas otorisasi yang dilakukan
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // AuthenticationManager -> verifikasi otorisasi penggunan dari bawaan spring security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // PasswordEncoder -> membuat password secara bcrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
