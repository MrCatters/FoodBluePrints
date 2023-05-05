package com.recipe.service.auth;

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

import com.recipe.model.users.Role;
import com.recipe.model.users.User;
import com.recipe.model.auth.AuthenticationRequest;
import com.recipe.model.auth.AuthenticationResponse;
import com.recipe.model.auth.RegisterRequest;
import com.recipe.model.token.Token;
import com.recipe.model.token.TokenRepository;
import com.recipe.model.token.TokenType;
import com.recipe.model.users.UserRepository;
import com.recipe.service.token.JwtService;

import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final User userProbe = new User();

  // Register a new user
  public AuthenticationResponse register(RegisterRequest request) {

    // Check if user already exists.
    userProbe.setEmail(request.getEmail());
    ExampleMatcher userMatcher = ExampleMatcher.matching()
        .withIgnorePaths("id")
        .withMatcher("model", new GenericPropertyMatcher().ignoreCase());
    Example<User> example = Example.of(userProbe, userMatcher);

    if (userRepository.exists(example)) {
      return null;
    }

    var user = User.builder()
        .firstName(request.getFirstname())
        .lastName(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    var savedUser = userRepository.save(user);
    var jwtToken = jwtService.generateJwtToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  // Authenticate a user
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()));
    var user = userRepository.findByEmail(request.getEmail())
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

  // Saves the user token in the database
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

  // Revokes all the user tokens
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

  // Refreshes the user token
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, StreamWriteException, DatabindException, java.io.IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.userRepository.findByEmail(userEmail)
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

  // Returns the user from the request
  public User getUser(HttpServletRequest httpServletRequest) {
    final String authHeader = httpServletRequest.getHeader("Authorization");
    final String jwtToken = authHeader.split(" ")[1].trim();
    final String userName = jwtService.extractUsername(jwtToken);
    final User existingUser = userRepository.findByEmail(userName)
        .orElseThrow();
    return existingUser;
  }
}