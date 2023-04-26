package com.recipe.controller.recipe;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.model.recipe.Recipe;
import com.recipe.model.recipe.RecipeDeletionRequest;
import com.recipe.model.recipe.RecipeResponse;
import com.recipe.model.recipe.RecipesRequest;
import com.recipe.model.recipe.UserRecipePost;
import com.recipe.service.recipe.RecipeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin({ "http://127.0.0.1:3000", "http://localhost:3000" })
@RequestMapping("/api/v1/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/post_recipe")
    public ResponseEntity<HttpStatus> postRecipe(
            @RequestBody UserRecipePost post,
            HttpServletRequest httpServletRequest) throws Exception {
        recipeService.postRecipe(post, httpServletRequest);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/name_recipes")
    public ResponseEntity<RecipeResponse> recipesByName(
            @RequestBody RecipesRequest request) throws Exception {
        return ResponseEntity.ok(recipeService.getRecipesByName(request));
    }

    @GetMapping("/user_first_name_recipes")
    public ResponseEntity<RecipeResponse> recipesByFirstUserName(
            @RequestBody RecipesRequest request) throws Exception {
        return ResponseEntity.ok(recipeService.getRecipesByUserFirstName(request));
    }

    @GetMapping("/user_last_name_recipes")
    public ResponseEntity<RecipeResponse> recipesByLastUserName(
            @RequestBody RecipesRequest request) throws Exception {
        return ResponseEntity.ok(recipeService.getRecipesByUserLastName(request));
    }

    @PostMapping("/user_email_recipes")
    public ResponseEntity<RecipeResponse> recipesByUserEmail(
            @RequestBody RecipesRequest request) throws Exception {
        return ResponseEntity.ok(recipeService.getRecipesByUserEmail(request));
    }

    @PostMapping("/delete_recipe")
    public ResponseEntity<HttpStatus> removeRecipeById(
            @RequestBody RecipeDeletionRequest request) {
        try {
            recipeService.deleteRecipesById(request.getRecipeId());
        } catch (Exception e) {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/auth_users_recipes")
    public ResponseEntity<RecipeResponse> getRecipeByAuth(
            HttpServletRequest httpServletRequest) throws Exception {
        return ResponseEntity.ok(recipeService.getRecipeByAuth(httpServletRequest));
    }

    @PutMapping("/put_recipe")
    public ResponseEntity<Object> putRecipeEntity(
        @RequestBody Recipe updatedRecipe,
        HttpServletRequest httpServletRequest) throws Exception {
            try{
                return ResponseEntity.ok(recipeService.putRecipeEntity(updatedRecipe, httpServletRequest));
            } catch (Exception)
        }
}
