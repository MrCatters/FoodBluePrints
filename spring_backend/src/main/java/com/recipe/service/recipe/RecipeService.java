package com.recipe.service.recipe;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.recipe.model.recipe.Recipe;
import com.recipe.model.recipe.RecipeRepository;
import com.recipe.model.recipe.RecipeResponse;
import com.recipe.model.recipe.RecipesRequest;
import com.recipe.model.recipe.UserRecipePost;
import com.recipe.model.users.User;
import com.recipe.service.auth.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final AuthenticationService authenticationService;

    public void postRecipe(UserRecipePost post, HttpServletRequest httpServletRequest) throws Exception{
        User existingUser = (authenticationService.getUser(httpServletRequest));


        final Recipe newRecipe = Recipe.builder()
                                .dateCreated(LocalDateTime.now())
                                .dateUpdated(LocalDateTime.now())
                                .name(post.getName())
                                .image(Base64
                                        .getDecoder()
                                        .decode(
                                        post.getImage()
                                        .getBytes()))
                                .user(existingUser)
                                .contents(post.getContents())
                                .build();
        recipeRepository.save(newRecipe);
    }
    
    public RecipeResponse getRecipesByName(RecipesRequest request) {
        List<Recipe> recipes = recipeRepository.findByNameContaining(request.getSearchString());
        return buildRecipeResponse(recipes);
    }

    public RecipeResponse getRecipesByUserFirstName(RecipesRequest request) {
        List<Recipe> recipes = recipeRepository.findAllRecipesByFirstName(request.getSearchString());
        return buildRecipeResponse(recipes);
    }

    public RecipeResponse getRecipesByUserLastName(RecipesRequest request) {
        List<Recipe> recipes = recipeRepository.findAllRecipesByLastName(request.getSearchString());
        return buildRecipeResponse(recipes);
    }

    public RecipeResponse getRecipesByUserEmail(RecipesRequest request) {
        List<Recipe> recipes = recipeRepository.findAllRecipesByEmail(request.getSearchString());
        return buildRecipeResponse(recipes);
    }


    public static RecipeResponse buildRecipeResponse(List<Recipe> recipes) {
        return RecipeResponse.builder()
                            .recipes(recipes)
                            .build();
    }

    public void deleteRecipesById(Integer recipeId) throws IllegalArgumentException{
        recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException());
        recipeRepository.deleteById(recipeId);
    }
}
