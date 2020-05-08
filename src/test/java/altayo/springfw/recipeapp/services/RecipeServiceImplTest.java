package altayo.springfw.recipeapp.services;

import altayo.springfw.recipeapp.converters.RecipeCommandToRecipe;
import altayo.springfw.recipeapp.converters.RecipeToRecipeCommand;
import altayo.springfw.recipeapp.models.Recipe;
import altayo.springfw.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        // Return recipesData when findAll() is called
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());

        // Verifying findAll() is called once
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe returned = recipeService.findById(1L);

        assertNotNull(returned);
        assertEquals(1, returned.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }
}