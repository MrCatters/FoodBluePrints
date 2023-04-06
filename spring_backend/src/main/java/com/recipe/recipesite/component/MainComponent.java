package com.recipe.recipesite.component;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class MainComponent {
    
    @GetMapping("/index")
    public String index(){
        return "index.html";
    }

}
