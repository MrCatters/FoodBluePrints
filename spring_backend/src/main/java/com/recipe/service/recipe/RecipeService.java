package com.recipe.service.recipe;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.recipe.model.recipe.Recipe;
import com.recipe.model.recipe.RecipeRepository;
import com.recipe.model.recipe.RecipeResponse;
import com.recipe.model.recipe.RecipeSearchRequest;
import com.recipe.model.recipe.RecipeDTO;
import com.recipe.model.recipe.RecipeIdRequest;
import com.recipe.model.users.User;
import com.recipe.model.users.UserRepository;
import com.recipe.service.auth.AuthenticationService;
import com.recipe.utils.NullUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    // Add new recipe to database
    public void postRecipe(RecipeDTO post, HttpServletRequest httpServletRequest) {
        User existingUser = (authenticationService.getUser(httpServletRequest));

        final Recipe newRecipe = Recipe.builder()
                .dateCreated(LocalDateTime.now())
                .dateUpdated(LocalDateTime.now())
                .name(post.getName())
                .image(Base64.getDecoder().decode(post.getImage().getBytes()))
                .user(existingUser)
                .contents(post.getContents())
                .build();
        recipeRepository.save(newRecipe);
    }

    // Get recipe by name
    public RecipeResponse getRecipesByName(RecipeSearchRequest request) {
        List<Recipe> recipes = recipeRepository.findFirst10ByNameContainingIgnoreCase(request.getSearchString());
        return buildRecipeResponse(recipes);
    }

    // Get recipes by user first name
    public RecipeResponse getRecipesByUserFirstName(RecipeSearchRequest request) {
        List<Recipe> recipes = recipeRepository.findAllRecipesByFirstName(request.getSearchString());
        return buildRecipeResponse(recipes);
    }

    // Get recipes by user last name
    public RecipeResponse getRecipesByUserLastName(RecipeSearchRequest request) {
        List<Recipe> recipes = recipeRepository.findAllRecipesByLastName(request.getSearchString());
        return buildRecipeResponse(recipes);
    }

    // Get recipes by user email
    public RecipeResponse getRecipesByUserEmail(RecipeSearchRequest request) {
        List<Recipe> recipes = recipeRepository.findAllRecipesByEmail(request.getSearchString());
        return buildRecipeResponse(recipes);
    }

    // Get recipes by recipe id
    public void deleteRecipesById(
            RecipeIdRequest deletionRequest,
            HttpServletRequest httpServletRequest)
            throws IllegalArgumentException {
        Integer recipeId = deletionRequest.getRecipeId();
        Integer userId = authenticationService.getUser(httpServletRequest).getId();

        if (recipeRepository.checkMatchingRecipe(recipeId, userId)) {
            recipeRepository.deleteById(recipeId);
        } else {
            throw new IllegalArgumentException("Recipe user id does not match with authorized user id.");
        }
    }

    // Get recipes by user auth
    public RecipeResponse getRecipeByAuth(HttpServletRequest httpServletRequest) {
        User existingUser = authenticationService.getUser(httpServletRequest);
        List<Recipe> recipes = recipeRepository.findAllRecipesByUserId(existingUser.getId());
        return buildRecipeResponse(recipes);
    }

    // Get a specified number of recent recipes
    public RecipeResponse getRecentRecipes(RecipeSearchRequest request) {
        List<Recipe> recipes = recipeRepository.findTopRecipes(Integer.parseInt(request.getSearchString()));
        return buildRecipeResponse(recipes);
    }

    // Modify certain recipe fields
    public void patchRecipeEntity(
            Recipe updatedRecipe,
            HttpServletRequest httpServletRequest)
            throws IllegalArgumentException {

        Integer recipeId = updatedRecipe.getId();
        Integer userId = authenticationService.getUser(httpServletRequest).getId();

        if (recipeRepository.checkMatchingRecipe(recipeId, userId)) {
            Recipe existingRecipe = recipeRepository.findById(recipeId)
                    .orElseThrow(
                            () -> new IllegalArgumentException("Recipe id does not match with an existing recipe"));
            NullUtils.updateIfPresent(existingRecipe::setImage, updatedRecipe.getImage());
            NullUtils.updateIfPresent(existingRecipe::setName, updatedRecipe.getName());
            NullUtils.updateIfPresent(existingRecipe::setContents, updatedRecipe.getContents());
            existingRecipe.setDateUpdated(LocalDateTime.now());
            recipeRepository.save(existingRecipe);
        } else {
            throw new IllegalArgumentException("Recipe user id does not match with authorized user id.");
        }
    }

    // Favorite an existing recipe
    public void addFavoriteRecipe(
            HttpServletRequest httpServletRequest,
            RecipeIdRequest recipeIdRequest)
            throws IllegalArgumentException {

        User existingUser = authenticationService.getUser(httpServletRequest);
        Integer recipeId = recipeIdRequest.getRecipeId();
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find recipe by provided id."));

        List<Recipe> favoritedRecipes = existingUser.getFavoritedRecipes();
        if (favoritedRecipes.contains(existingRecipe)) {
            throw new IllegalArgumentException("Recipe has already been favorited.");
        }

        favoritedRecipes.add(existingRecipe);
        existingUser.setFavoritedRecipes(favoritedRecipes);
        userRepository.save(existingUser);
    }

    // Get all favorited recipes
    public RecipeResponse getUserFavoritedRecipes(HttpServletRequest httpServletRequest) {
        User existingUser = authenticationService.getUser(httpServletRequest);
        List<Recipe> recipes = recipeRepository.findAllFavoritedRecipesByUserId(existingUser.getId());
        return buildRecipeResponse(recipes);
    }

    // Remove a user favorited recipe
    public void removeFavoriteRecipe(
            HttpServletRequest httpServletRequest,
            RecipeSearchRequest recipesRequest)
            throws ResourceNotFoundException {
        User existingUser = authenticationService.getUser(httpServletRequest);
        Integer recipeId = Integer.parseInt(recipesRequest.getSearchString());
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find recipe by provided id."));

        List<Recipe> favoritedRecipes = existingUser.getFavoritedRecipes();
        if (!favoritedRecipes.remove(existingRecipe)) {
            throw new ResourceNotFoundException("Unable to find matching favorited recipe by provided id.");
        }

        existingUser.setFavoritedRecipes(favoritedRecipes);
        userRepository.save(existingUser);
    }

    // Helper method to build recipe response
    public static RecipeResponse buildRecipeResponse(List<Recipe> recipes) {
        return RecipeResponse.builder()
                .recipes(recipes)
                .build();
    }
}
