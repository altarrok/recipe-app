package altayo.springfw.recipeapp.controllers;

import altayo.springfw.recipeapp.commands.IngredientCommand;
import altayo.springfw.recipeapp.commands.RecipeCommand;
import altayo.springfw.recipeapp.services.IngredientService;
import altayo.springfw.recipeapp.services.RecipeService;
import altayo.springfw.recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RecipeIngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public RecipeIngredientController(RecipeService recipeService,
                                      IngredientService ingredientService,
                                      UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String indexRecipeIngredients(@PathVariable String recipeId, Model model) {
        RecipeCommand foundRecipeCommand = recipeService.findCommandById(new Long(recipeId));
        model.addAttribute("recipe", foundRecipeCommand);
        return "recipe/ingredient/index";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{ingredientId}")
    public String showRecipeIngredients(@PathVariable String recipeId,
                                        @PathVariable String ingredientId,
                                        Model model) {
        IngredientCommand ingredientCommand = ingredientService.findCommandById(new Long(ingredientId));
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/create")
    public String createRecipeIngredients(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("uomList", unitOfMeasureService.findAllCommand());
        model.addAttribute("ingredient", new IngredientCommand());
        return "recipe/ingredient/create";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{ingredientId}/edit")
    public String editRecipeIngredients(@PathVariable String recipeId,
                                        @PathVariable String ingredientId,
                                        Model model) {
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("uomList", unitOfMeasureService.findAllCommand());
        model.addAttribute("ingredient", ingredientService.findCommandById(new Long(ingredientId)));
        return "recipe/ingredient/create";
    }

    @PostMapping("/recipe/{recipeId}/ingredients/store")
    public String storeRecipeIngredients(@PathVariable String recipeId,
                                         @ModelAttribute IngredientCommand ingredientCommand) {
        ingredientService.saveIngredientCommand(ingredientCommand, new Long(recipeId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{ingredientId}/delete")
    public String deleteRecipeIngredients(@PathVariable String recipeId, @PathVariable String ingredientId) {
        IngredientCommand ingredientCommand = ingredientService.findCommandById(new Long(ingredientId));
        ingredientService.deleteIngredientCommand(ingredientCommand, new Long(recipeId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
