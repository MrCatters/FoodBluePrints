package com.recipe.controller.user;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.model.users.User;
import com.recipe.model.users.UserInformationResponse;
import com.recipe.service.auth.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin({ "http://127.0.0.1:3000", "http://localhost:3000" })
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    // Get user information
    @GetMapping("/user_information")
    public UserInformationResponse userInformation(
            String authHeader, HttpServletRequest httpServletRequest) {
        User existingUser = (authenticationService.getUser(httpServletRequest));

        return UserInformationResponse.builder()
                .id(existingUser.getId())
                .firstName(existingUser.getFirstName())
                .lastName(existingUser.getLastName())
                .email(existingUser.getEmail())
                .build();
    }
}
