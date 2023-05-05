package com.recipe.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.recipe.model.recipe.Recipe;
import com.recipe.model.recipe.RecipeRepository;
import com.recipe.model.users.User;
import com.recipe.model.users.UserRepository;

@Component
public class DemoLoader implements CommandLineRunner {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Recipe> recipes = new ArrayList<>();

        String[] names = { "Creamy Garlic Shrimp Pasta", "Banana Bread", "Caesar Salad", "Chocolate Chip Cookies",
                "Spicy Chicken Tacos" };
        String[] images = { "shrimp.jpg", "banana_bread.jpg", "caesar_salad.jpg", "cookies.jpg", "tacos.jpg" };
        String[] contents = { """
                # Creamy Garlic Shrimp Pasta
                This recipe is perfect for a quick and easy dinner that will impress your family or guests.
                ## Ingredients
                - 1 lb. spaghetti pasta
                - 1 lb. shrimp, peeled and deveined
                - 4 cloves garlic, minced
                - 1/2 cup heavy cream
                - 1/2 cup grated Parmesan cheese
                - 2 tablespoons butter
                - 2 tablespoons olive oil
                - Salt and pepper to taste
                ## Instructions
                1. Cook the pasta according to package instructions. Drain and set aside.
                2. In a large skillet, heat the olive oil and butter over medium heat.
                3. Add the garlic and sauté for 1-2 minutes until fragrant.
                4. Add the shrimp to the skillet and cook until pink, about 3-4 minutes per side.
                5. Pour in the heavy cream and grated Parmesan cheese, and stir until the sauce is smooth and creamy.
                6. Add the cooked pasta to the skillet and toss until evenly coated with the sauce.
                7. Season with salt and pepper to taste.
                8. Serve hot and enjoy!
                """,
                """
                        This delicious and easy-to-make banana bread is perfect for breakfast, snack, or dessert.

                        ## Ingredients
                        - 2 cups all-purpose flour
                        - 1 teaspoon baking soda
                        - 1/4 teaspoon salt
                        - 1/2 cup unsalted butter, softened
                        - 1 cup granulated sugar
                        - 2 large eggs
                        - 3 ripe bananas, mashed
                        - 1/3 cup plain Greek yogurt
                        - 1 teaspoon vanilla extract

                        ## Instructions
                        1. Preheat the oven to 350°F (175°C). Grease a 9x5 inch loaf pan with cooking spray.
                        2. In a medium bowl, whisk together the flour, baking soda, and salt.
                        3. In a separate large mixing bowl, cream together the butter and sugar until light and fluffy.
                        4. Add the eggs one at a time, mixing well after each addition.
                        5. Stir in the mashed bananas, Greek yogurt, and vanilla extract until well combined.
                        6. Gradually add the flour mixture to the banana mixture and stir until just combined.
                        7. Pour the batter into the prepared loaf pan and bake for 50-60 minutes, or until a toothpick inserted into the center comes out clean.
                        8. Allow the banana bread to cool in the pan for 10 minutes before removing it and placing it on a wire rack to cool completely.
                        9. Slice and serve, and enjoy your delicious classic banana bread!
                        """,
                """
                        This salad is a favorite for a reason. It's simple, yet satisfying and delicious!

                        ## Ingredients

                        - 1 head of romaine lettuce, washed and chopped
                        - 1/2 cup croutons
                        - 1/4 cup grated Parmesan cheese
                        - 2 tablespoons fresh lemon juice
                        - 2 cloves garlic, minced
                        - 1/2 teaspoon Dijon mustard
                        - 1/2 cup olive oil
                        - Salt and pepper to taste

                        ## Instructions

                        1. In a small bowl, whisk together the lemon juice, garlic, Dijon mustard, olive oil, salt, and pepper to make the dressing.
                        2. In a large bowl, add the chopped romaine lettuce and pour the dressing over it. Toss to coat the lettuce leaves evenly.
                        3. Add the croutons to the bowl and toss again.
                        4. Sprinkle the grated Parmesan cheese on top of the salad and serve.

                        Enjoy your delicious classic Caesar salad!
                        """,
                """
                        ## Ingredients
                        - 2 1/4 cups all-purpose flour
                        - 1 tsp. baking soda
                        - 1 tsp. salt
                        - 1 cup unsalted butter, softened
                        - 3/4 cup granulated sugar
                        - 3/4 cup brown sugar, packed
                        - 2 large eggs
                        - 1 tsp. vanilla extract
                        - 2 cups semisweet chocolate chips

                        ## Instructions
                        1. Preheat the oven to 375°F (190°C).
                        2. In a medium bowl, whisk together the flour, baking soda, and salt.
                        3. In a large bowl, beat the softened butter, granulated sugar, and brown sugar with an electric mixer on medium speed until light and fluffy.
                        4. Beat in the eggs and vanilla extract until well combined.
                        5. Gradually add the flour mixture to the butter mixture, beating on low speed until just combined.
                        6. Stir in the chocolate chips.
                        7. Drop rounded tablespoons of dough onto ungreased baking sheets.
                        8. Bake for 10-12 minutes or until the edges are golden brown.
                        9. Let the cookies cool on the baking sheets for 5 minutes before transferring them to wire racks to cool completely.
                        10. Serve and enjoy!
                        """,
                """
                        ## Ingredients
                        - 1 lb. boneless, skinless chicken breasts, cut into bite-sized pieces
                        - 2 tablespoons olive oil
                        - 2 cloves garlic, minced
                        - 1 teaspoon chili powder
                        - 1 teaspoon cumin
                        - 1/2 teaspoon paprika
                        - 1/4 teaspoon cayenne pepper
                        - Salt and pepper to taste
                        - 8-10 small corn tortillas
                        - 1/2 cup chopped fresh cilantro
                        - 1/2 cup diced onion
                        - 1/2 cup crumbled queso fresco
                        - Lime wedges for serving

                        ## Instructions
                        1. Heat the olive oil in a large skillet over medium-high heat.
                        2. Add the chicken to the skillet and cook until browned on all sides, about 5-6 minutes.
                        3. Add the garlic, chili powder, cumin, paprika, cayenne pepper, salt, and pepper to the skillet, and stir until the chicken is evenly coated.
                        4. Reduce the heat to medium-low and simmer for another 5-7 minutes until the chicken is cooked through.
                        5. Warm the tortillas in a dry skillet over medium-high heat for about 30 seconds per side.
                        6. Divide the chicken evenly among the tortillas.
                        7. Top each taco with chopped cilantro, diced onion, and crumbled queso fresco.
                        8. Serve with lime wedges on the side.
                        9. Enjoy your delicious and spicy chicken tacos!
                        """
        };

        // Create a dummy user
        // Get the relative location of the directory holding images.
        User test = new User();
        userRepository.save(test);

        for (int i = 0; i < names.length; i++) {
            recipes.add(Recipe.builder()
                    .name(names[i])
                    .user(test)
                    .contents(contents[i])
                    .dateCreated(LocalDateTime.now())
                    .dateUpdated(LocalDateTime.now())
                    .image(getClass().getClassLoader().getResourceAsStream("images/" + images[i]).readAllBytes())
                    .build());
        }

        recipeRepository.saveAll(recipes);
    }

}
