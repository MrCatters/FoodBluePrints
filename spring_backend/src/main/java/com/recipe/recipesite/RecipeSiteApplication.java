package com.recipe.recipesite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class RecipeSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeSiteApplication.class, args);
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
