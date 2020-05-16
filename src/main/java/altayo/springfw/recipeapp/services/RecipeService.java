package altayo.springfw.recipeapp.services;

import altayo.springfw.recipeapp.commands.RecipeCommand;
import altayo.springfw.recipeapp.models.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long aLong);

    void deleteById(Long aLong);

    void saveImageFile(Long id, MultipartFile file);
}
