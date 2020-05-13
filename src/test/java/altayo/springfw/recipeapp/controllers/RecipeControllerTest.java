package altayo.springfw.recipeapp.controllers;

import altayo.springfw.recipeapp.commands.RecipeCommand;
import altayo.springfw.recipeapp.models.Recipe;
import altayo.springfw.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void showRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe", recipe))
                .andExpect(view().name("recipe/show"));
    }

    @Test
    void createRecipe() throws Exception {
        mockMvc.perform(get("/recipe/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/create"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void storeRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe/store")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "Something"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2"));
    }

    @Test
    void editRecipe() throws Exception {
        RecipeCommand recipe = new RecipeCommand();
        recipe.setId(3L);

        when(recipeService.findCommandById(3L)).thenReturn(recipe);

        mockMvc.perform(get("/recipe/3/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/edit"));
    }
}