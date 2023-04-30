package com.recipe.model.recipe;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

@Transactional
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

        Optional<Recipe> findById(Integer id);

        void deleteById(Integer id);

        List<Recipe> findFirst10ByNameContaining(String name);

        @Query(value = """
                        SELECT t.* FROM recipes t\s
                        INNER JOIN _user u\s
                        ON t.user_id = u.id\s
                        WHERE u.first_name LIKE :first\s
                        """, nativeQuery = true)
        List<Recipe> findAllRecipesByFirstName(@Param("first") String firstName);

        @Query(value = """
                        SELECT t.* FROM recipes t\s
                        INNER JOIN _user u\s
                        ON t.user_id = u.id\s
                        WHERE u.last_name LIKE :last\s
                        """, nativeQuery = true)
        List<Recipe> findAllRecipesByLastName(@Param("last") String lastName);

        @Query(value = """
                        SELECT t.* FROM recipes t\s
                        INNER JOIN _user u\s
                        ON t.user_id = u.id\s
                        WHERE u.email LIKE :email\s
                        """, nativeQuery = true)
        List<Recipe> findAllRecipesByEmail(@Param("email") String email);

        @Query(value = """
                        SELECT t.* FROM recipes t\s
                        WHERE t.user_id = :user_id\s
                        """, nativeQuery = true)
        List<Recipe> findAllRecipesByUserId(@Param("user_id") Integer userId);

        @Query(value = """
                        SELECT case WHEN count(t)> 0\s
                        THEN true ELSE false END\s
                        FROM recipes t\s
                        WHERE t.id = :recipe_id\s
                        AND t.user_id = :user_id\s
                        """, nativeQuery = true)
        Boolean checkMatchingRecipe(@Param("recipe_id") Integer recipeId, @Param("user_id") Integer userId);

        @Query(value = """
                        SELECT r.* FROM recipes r\s
                        ORDER BY date_created ASC\s
                        LIMIT :amount\s
                        """, nativeQuery = true)
        List<Recipe> findTopRecipes(@Param("amount") int num);

        @Query(value = """
                SELECT r.* FROM recipes r\s
                INNER JOIN _user_favorited_recipes fr 
                        ON fr.favorited_recipes_id = r.id\s
                        WHERE fr.user_id = :user_id\s
                """, nativeQuery = true)
        List<Recipe> findAllFavoritedRecipesByUserId(@Param("user_id") Integer userId);
}