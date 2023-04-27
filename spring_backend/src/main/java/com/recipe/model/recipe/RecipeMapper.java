package com.recipe.model.recipe;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecipeMapper {
    void updateCustomerFromDto(RecipeResponse recipeDto, Recipe entity);
}