package altayo.springfw.recipeapp.controllers;

import altayo.springfw.recipeapp.commands.IngredientCommand;
import altayo.springfw.recipeapp.commands.RecipeCommand;
import altayo.springfw.recipeapp.services.IngredientService;
import altayo.springfw.recipeapp.services.RecipeService;
import altayo.springfw.recipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeIngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    RecipeIngredientController recipeIngredientController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeIngredientController).build();
    }

    @Test
    void indexRecipeIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/index"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void showRecipeIngredients() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findCommandById(anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findCommandById(anyLong());
    }

    @Test
    void editRecipeIngredients() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findCommandById(anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredients/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/create"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("recipeId"))
                .andExpect(model().attributeExists("uomList"));

        verify(ingredientService, times(1)).findCommandById(anyLong());
        verify(unitOfMeasureService, times(1)).findAllCommand();
    }

    @Test
    void createRecipeIngredients() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredients/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/create"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("recipeId"))
                .andExpect(model().attributeExists("uomList"));

        verify(unitOfMeasureService, times(1)).findAllCommand();
        verifyNoInteractions(ingredientService);
    }

    @Test
    void storeRecipeIngredients() throws Exception {
        mockMvc.perform(post("/recipe/3/ingredients/store")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/3/ingredients"));

        verify(ingredientService, times(1)).saveIngredientCommand(any(), anyLong());
    }
}