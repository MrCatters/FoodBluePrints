package com.recipe.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.recipe.model.auth.AuthenticationRequest;
import com.recipe.model.auth.AuthenticationResponse;
import com.recipe.model.auth.RegisterRequest;
import com.recipe.service.auth.AuthenticationService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin({ "http://127.0.0.1:3000", "http://localhost:3000" })
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) throws Exception {
        if (service.register(request) == null) {
            return ResponseEntity.badRequest().body(service.register(request));
        }

        return ResponseEntity.ok(service.register(request));
    }

    // Authenticate a user
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    // Refresh a token
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, StreamWriteException, DatabindException, java.io.IOException {
        service.refreshToken(request, response);
    }
}