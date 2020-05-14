package altayo.springfw.recipeapp.services;

import altayo.springfw.recipeapp.commands.IngredientCommand;
import altayo.springfw.recipeapp.converters.IngredientCommandToIngredient;
import altayo.springfw.recipeapp.converters.IngredientToIngredientCommand;
import altayo.springfw.recipeapp.models.Ingredient;
import altayo.springfw.recipeapp.models.Recipe;
import altayo.springfw.recipeapp.repositories.IngredientRepository;
import altayo.springfw.recipeapp.repositories.RecipeRepository;
import altayo.springfw.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public void saveIngredientCommand(IngredientCommand command, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("RECIPE NOT FOUND"));

        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient1 -> ingredient1.getId().equals(command.getId()))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient foundIngredient = ingredientOptional.get();
            foundIngredient.setAmount(command.getAmount());
            foundIngredient.setDescription(command.getDescription());
            foundIngredient.setUnitOfMeasure(unitOfMeasureRepository.findById(command.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UNIT OF MEASURE NOT FOUND: " + command.getUnitOfMeasure())));
        } else {
            recipe.addIngredient(ingredientCommandToIngredient.convert(command));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);
    }

    @Override
    public IngredientCommand findCommandById(Long aLong) {
        return ingredientToIngredientCommand.convert(findById(aLong));
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteIngredientCommand(IngredientCommand ingredientCommand, Long aLong) {
        Recipe recipe = recipeRepository.findById(aLong).orElseThrow(() -> new RuntimeException("RECIPE NOT FOUND"));

        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

        recipe.getIngredients().remove(ingredient);

        recipeRepository.save(recipe);
        ingredientRepository.delete(ingredient);
    }
}
