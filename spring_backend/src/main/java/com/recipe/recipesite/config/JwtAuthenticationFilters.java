package com.recipe.recipesite.config;

import java.io.IOException;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.recipe.recipesite.service.JwtService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // Gen constructor relating to final.
public class JwtAuthenticationFilters extends OncePerRequestFilter {

    private final JwtService jwtService;

    // Intercept requests and respond with new data
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain // COR design pattern
    )throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;
        // Pass to next filter
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        // userEmail = jwtService.extract
    }

}
