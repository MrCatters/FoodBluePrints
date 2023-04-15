package com.recipe.recipesite.service.recipe;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recipe.recipesite.model.recipe.RecipeRepository;
import com.recipe.recipesite.model.recipe.RecipeResponse;
import com.recipe.recipesite.model.recipe.UserRecipesRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeResponse usersRecipes(UserRecipesRequest request) {
        List<String> recipes = recipeRepository.findRecipeByUser(request.getUser());
        return RecipeResponse.builder()
                            .recipes(recipes)
                            .build();
    }
}
