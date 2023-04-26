package com.recipe.model.recipe;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

@Transactional
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByNameContaining(String name);

    @Query(value = """
        SELECT t.* FROM recipes t\s 
        INNER JOIN _user u\s
        ON t.user_id = u.id\s 
        WHERE u.first_name LIKE :first\s
        """,
        nativeQuery=true)
    List<Recipe> findAllRecipesByFirstName(@Param("first") String firstName);

    @Query(value = """
        SELECT t.* FROM recipes t\s 
        INNER JOIN _user u\s
        ON t.user_id = u.id\s 
        WHERE u.last_name LIKE :last\s
        """,
        nativeQuery=true)
    List<Recipe> findAllRecipesByLastName(@Param("last") String lastName);

    @Query(value = """
        SELECT t.* FROM recipes t\s 
        INNER JOIN _user u\s
        ON t.user_id = u.id\s 
        WHERE t.email LIKE :email\s
        """,
        nativeQuery=true)
    List<Recipe> findAllRecipesByEmail(@Param("email") String email);

    void deleteById(Integer id);

    @Query(value = """
        SELECT t.* FROM recipes t\s 
        WHERE t.user_id = :user_id\s
        """,
        nativeQuery=true)
    List<Recipe> findAllRecipesByUserId(@Param("user_id") Integer userId);

    @Query(value = """
        SELECT case WHEN count(t)> 0\s
        THEN true ELSE false END\s
        FROM recipes t\s
        WHERE t.id = :recipe_id\s
        AND t.user_id = :user_id\s
        """,
        nativeQuery=true)
    Boolean checkMatchingRecipe(@Param("recipe_id") Integer recipeId, @Param("user_id") Integer userId);

    Optional<Recipe> findById(Integer id);
}
