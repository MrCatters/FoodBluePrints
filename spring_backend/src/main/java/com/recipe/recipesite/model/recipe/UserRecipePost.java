package com.recipe.recipesite.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRecipePost {
    private String name;
    private String image;
    private String dateCreated;
    private String dateUpdated;
    private String user;
}
