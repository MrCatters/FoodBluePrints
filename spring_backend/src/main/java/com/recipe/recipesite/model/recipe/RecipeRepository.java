package com.recipe.recipesite.model.recipe;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findByName(String name);

    
}
