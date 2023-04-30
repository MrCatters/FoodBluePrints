package com.recipe.service.recipe;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.recipe.model.recipe.Recipe;
import com.recipe.model.recipe.RecipeRepository;
import com.recipe.model.recipe.RecipeResponse;
import com.recipe.model.recipe.RecipesRequest;
import com.recipe.model.recipe.RecipeDTO;
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
    private Logger logger = org.apache.logging.log4j.LogManager.getLogger(RecipeService.class);

    public void postRecipe(RecipeDTO post, HttpServletRequest httpServletRequest) throws Exception {
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

    public RecipeResponse getRecipesByName(RecipesRequest request) {
        List<Recipe> recipes = recipeRepository.findFirst10ByNameContaining(request.getSearchString());
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

    public void deleteRecipesById(Integer recipeId) throws ResourceNotFoundException {
        recipeRepository.findById(recipeId).orElseThrow(() -> new ResourceNotFoundException());

        
        recipeRepository.deleteById(recipeId);
    }

    public RecipeResponse getRecipeByAuth(HttpServletRequest httpServletRequest) {
        User existingUser = authenticationService.getUser(httpServletRequest);
        List<Recipe> recipes = recipeRepository.findAllRecipesByUserId(existingUser.getId());
        return buildRecipeResponse(recipes);
    }

    public RecipeResponse getRecentRecipes(RecipesRequest request) {
        List<Recipe> recipes = recipeRepository.findTopRecipes(Integer.parseInt(request.getSearchString()));
        return buildRecipeResponse(recipes);
    }

    public void patchRecipeEntity(Recipe updatedRecipe, HttpServletRequest httpServletRequest)
            throws ResourceNotFoundException {

        Integer recipeId = updatedRecipe.getId();
        Integer userId = authenticationService.getUser(httpServletRequest).getId();

        if (recipeRepository.checkMatchingRecipe(recipeId, userId)) {
            Recipe existingRecipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Recipe id does not match with an existing recipe"));
            NullUtils.updateIfPresent(existingRecipe::setImage, updatedRecipe.getImage());
            NullUtils.updateIfPresent(existingRecipe::setName, updatedRecipe.getName());
            NullUtils.updateIfPresent(existingRecipe::setContents, updatedRecipe.getContents());
            existingRecipe.setDateUpdated(LocalDateTime.now());
            recipeRepository.save(existingRecipe);

        } else {
            throw new ResourceNotFoundException("Recipe user id does not match with authorized user");
        }
    }

    public RecipeResponse getUserFavoritedRecipes(HttpServletRequest httpServletRequest) {
        User existingUser = authenticationService.getUser(httpServletRequest);
        List<Recipe> recipes = recipeRepository.findAllFavoritedRecipesByUserId(existingUser.getId());
        return buildRecipeResponse(recipes);
    }

    public void addFavoriteRecipe(
            HttpServletRequest httpServletRequest,
            RecipesRequest recipesRequest) {
        User existingUser = authenticationService.getUser(httpServletRequest);
        Integer recipeId = Integer.parseInt(recipesRequest.getSearchString());
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find recipe by provided id."));

        List<Recipe> favoritedRecipes = existingUser.getFavoritedRecipes();
        for (Recipe recipe : favoritedRecipes) {
            if (recipe == existingRecipe) {
                throw new IllegalArgumentException("Recipe has already been favorited.");
            }
        }

        favoritedRecipes.add(existingRecipe);

        existingUser.setFavoritedRecipes(favoritedRecipes);
        userRepository.save(existingUser);
    }
}
