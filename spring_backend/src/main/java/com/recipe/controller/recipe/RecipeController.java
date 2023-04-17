package com.recipe.controller.recipe;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.model.recipe.RecipeResponse;
import com.recipe.model.recipe.RecipesRequest;
import com.recipe.model.recipe.UserRecipePost;
import com.recipe.service.recipe.RecipeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("http://127.0.0.1:3000")
@RequestMapping("/api/v1/recipe")
@RequiredArgsConstructor
public class RecipeController {
    
    private final RecipeService service;

    @PostMapping("/post_recipe")
    public ResponseEntity<HttpStatus> postRecipe(
        @RequestBody UserRecipePost post,
        HttpServletRequest httpServletRequest) throws Exception{
            service.postRecipe(post, httpServletRequest);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }

    @GetMapping("/name_recipes")
    public ResponseEntity<RecipeResponse> recipesByName(
        @RequestBody RecipesRequest request) throws Exception{
            return ResponseEntity.ok(service.getRecipesByName(request));
        }

    @GetMapping("/user_first_name_recipes")
    public ResponseEntity<RecipeResponse> recipesByFirstUserName(
        @RequestBody RecipesRequest request) throws Exception{
            return ResponseEntity.ok(service.getRecipesByFirstUserName(request));
        }
        
    @GetMapping("/user_last_name_recipes")
    public ResponseEntity<RecipeResponse> recipesByLastUserName(
        @RequestBody RecipesRequest request) throws Exception{
            return ResponseEntity.ok(service.getRecipesByLastUserName(request));
        }
}
