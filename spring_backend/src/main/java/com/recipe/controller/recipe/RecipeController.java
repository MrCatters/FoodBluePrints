package com.recipe.controller.recipe;

import java.util.logging.Logger;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.model.recipe.Recipe;
import com.recipe.model.recipe.RecipeIdRequest;
import com.recipe.model.recipe.RecipeResponse;
import com.recipe.model.recipe.RecipeSearchRequest;
import com.recipe.model.recipe.RecipeDTO;
import com.recipe.service.recipe.RecipeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin({ "http://127.0.0.1:3000", "http://localhost:3000" })
@RequestMapping("/api/v1/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService service;

    @PostMapping("/post_recipe")
    public ResponseEntity<HttpStatus> postRecipe(
            @RequestBody RecipeDTO post,
            HttpServletRequest httpServletRequest) {
        service.postRecipe(post, httpServletRequest);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping("/name_recipes")
    public ResponseEntity<RecipeResponse> recipesByName(
            @RequestBody RecipeSearchRequest request) {
        return ResponseEntity.ok(service.getRecipesByName(request));
    }

    @PostMapping("/user_first_name_recipes")
    public ResponseEntity<RecipeResponse> recipesByFirstUserName(
            @RequestBody RecipeSearchRequest request) {
        return ResponseEntity.ok(service.getRecipesByUserFirstName(request));
    }

    @PostMapping("/user_last_name_recipes")
    public ResponseEntity<RecipeResponse> recipesByLastUserName(
            @RequestBody RecipeSearchRequest request) {
        return ResponseEntity.ok(service.getRecipesByUserLastName(request));
    }

    @PostMapping("/user_email_recipes")
    public ResponseEntity<RecipeResponse> recipesByUserEmail(
            @RequestBody RecipeSearchRequest request) {
        return ResponseEntity.ok(service.getRecipesByUserEmail(request));
    }

    @DeleteMapping("/delete_recipe")
    public ResponseEntity<String> deleteRecipeById(
            @RequestBody RecipeIdRequest request,
            HttpServletRequest httpServletRequest) {
        try {
            service.deleteRecipesById(request, httpServletRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/auth_users_recipes")
    public ResponseEntity<RecipeResponse> getRecipeByAuth(
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(service.getRecipeByAuth(httpServletRequest));
    }

    @PostMapping("/recent_recipes")
    public ResponseEntity<RecipeResponse> recentRecipes(
            @RequestBody RecipeSearchRequest request) {
        return ResponseEntity.ok(service.getRecentRecipes(request));
    }

    @PatchMapping("/patch_recipe")
    public ResponseEntity<String> patchRecipe(
            @RequestBody Recipe recipe,
            HttpServletRequest httpServletRequest) {
        try {
            service.patchRecipeEntity(recipe, httpServletRequest);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/post_favorite_recipe")
    public ResponseEntity<String> addFavoriteRecipe(
            @RequestBody RecipeIdRequest recipeIdRequest,
            HttpServletRequest httpServletRequest) {
        try {
            service.addFavoriteRecipe(httpServletRequest, recipeIdRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete_favorite_recipe")
    public ResponseEntity<String> deleteFavoriteRecipe(
            @RequestBody RecipeSearchRequest recipesRequest,
            HttpServletRequest httpServletRequest) {
        try {
            service.removeFavoriteRecipe(httpServletRequest, recipesRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/favorited_recipes")
    public ResponseEntity<RecipeResponse> getUserFavoritedRecipes(
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(service.getUserFavoritedRecipes(httpServletRequest));
    }

}
