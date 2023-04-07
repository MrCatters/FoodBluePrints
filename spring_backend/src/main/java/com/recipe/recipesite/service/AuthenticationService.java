package com.recipe.recipesite.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.recipe.recipesite.model.users.Role;
import com.recipe.recipesite.model.users.User;
import com.recipe.recipesite.model.auth.AuthenticationRequest;
import com.recipe.recipesite.model.auth.AuthenticationResponse;
import com.recipe.recipesite.model.auth.RegisterRequest;
import com.recipe.recipesite.model.token.Token;
import com.recipe.recipesite.model.token.TokenRepository;
import com.recipe.recipesite.model.token.TokenType;
import com.recipe.recipesite.model.users.UserRepository;

import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final User userProbe = new User();
    
    public AuthenticationResponse register(RegisterRequest request) {
      
      // Check if user already exsists.
      userProbe.setEmail(request.getEmail());
      ExampleMatcher userMatcher = ExampleMatcher.matching()
        .withIgnorePaths("id")
        .withMatcher("model", new GenericPropertyMatcher().ignoreCase());
      Example<User> example = Example.of(userProbe, userMatcher);

      if (repository.exists(example))
        return null;

        var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateJwtToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user = repository.findByEmail(request.getEmail())
            .orElseThrow();
        var jwtToken = jwtService.generateJwtToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
      }
    
      private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
        tokenRepository.save(token);
      }
    
      private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
          return;
        validUserTokens.forEach(token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
      }
    
      public void refreshToken(
              HttpServletRequest request,
              HttpServletResponse response
      ) throws IOException, StreamWriteException, DatabindException, java.io.IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
          return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
          var user = this.repository.findByEmail(userEmail)
                  .orElseThrow();
          if (jwtService.isJwtTokenValid(refreshToken, user)) {
            var accessToken = jwtService.generateJwtToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);
            var authResponse = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
          }
        }
      }
    }
