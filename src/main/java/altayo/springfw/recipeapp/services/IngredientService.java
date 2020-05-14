package altayo.springfw.recipeapp.services;

import altayo.springfw.recipeapp.commands.IngredientCommand;
import altayo.springfw.recipeapp.models.Ingredient;

public interface IngredientService {
    Ingredient findById(Long id);

    void saveIngredientCommand(IngredientCommand command, Long recipeId);

    IngredientCommand findCommandById(Long aLong);

    void deleteById(Long aLong);

    void deleteIngredientCommand(IngredientCommand ingredientCommand, Long aLong);
}
