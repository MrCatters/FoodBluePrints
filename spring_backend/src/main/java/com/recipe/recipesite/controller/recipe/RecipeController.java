package com.recipe.recipesite.controller.recipe;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("http://127.0.0.1:3000")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RecipeController {
    
}
