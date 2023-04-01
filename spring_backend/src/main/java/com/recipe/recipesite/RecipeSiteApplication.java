package com.recipe.recipesite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class RecipeSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeSiteApplication.class, args);
    }

    @GetMapping("/")
    public String index(){
        return "index.html";
    }

}
