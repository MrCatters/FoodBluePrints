package com.recipe.recipesite.model.recipe;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findByName(String name);

    @Query(value = """
            SELECT r FROM recipe\s
            r INNER JOIN User u\s
            ON r.user.id = u.id\s
            """)
    List<String> findRecipeByUser(String string);

}
