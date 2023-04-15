package com.recipe.recipesite.controller.recipe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.recipesite.model.recipe.RecipeResponse;
import com.recipe.recipesite.model.recipe.UserRecipesRequest;
import com.recipe.recipesite.model.users.UserRepository;
import com.recipe.recipesite.service.recipe.RecipeService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("http://127.0.0.1:3000")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RecipeController {
    
    private final RecipeService service;

    @GetMapping("/publishedRecipes")
    public ResponseEntity<RecipeResponse> publishedRecipes(
        @RequestBody UserRecipesRequest request) throws Exception{
            return ResponseEntity.ok(service.usersRecipes(request))
        }
}
