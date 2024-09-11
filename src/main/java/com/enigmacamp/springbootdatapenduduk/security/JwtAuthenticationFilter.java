package com.enigmacamp.springbootdatapenduduk.security;

import com.enigmacamp.springbootdatapenduduk.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-data-penduduk
 * User: jutioncandrakirana
 * Email: jutionck@gmail.com
 * Telegram : jutionck
 * Date: 11/09/24
 * Time: 10.36
 * To change this template use File | Settings | File Templates.
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Mendapatkan header Authorization
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        // Memeriksa apakah header Authorization ada dan memiliki format "Bearer token"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Jika tidak ada token, lanjutkan filter berikutnya
            return;
        }

        // Mengambil token dari header Authorization
        token = authHeader.replace("Bearer ", "");

        // Ekstrak username dari token JWT
        username = jwtService.extractUsername(token);
        System.out.println("username: " + username);

        // Memeriksa apakah username ada dan apakah pengguna belum terautentikasi
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Mengambil detail pengguna dari userDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // Memeriksa apakah token valid
            if (jwtService.isTokenValid(token, userDetails)) {
                // Membuat token autentikasi dan menaruhnya di dalam konteks keamanan Spring
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); // Set autentikasi di konteks
            }
        }

        // Lanjutkan ke filter berikutnya
        filterChain.doFilter(request, response);
    }
}
