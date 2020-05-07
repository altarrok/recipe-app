package altayo.springfw.recipeapp.services;

import altayo.springfw.recipeapp.models.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);
}
