package altayo.springfw.recipeapp.services;

import altayo.springfw.recipeapp.models.Ingredient;
import altayo.springfw.recipeapp.repositories.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    IngredientRepository ingredientRepository;

    @InjectMocks
    IngredientServiceImpl ingredientServiceImpl;

    @Test
    void findById() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        when(ingredientRepository.findById(1L)).thenReturn(java.util.Optional.of(ingredient));

        Ingredient returnedIngredient = ingredientServiceImpl.findById(1L);

        assertNotNull(returnedIngredient);
        assertEquals(1L, returnedIngredient.getId());
        verify(ingredientRepository, times(1)).findById(anyLong());


    }

    @Test
    void saveIngredientCommand() {

    }

    @Test
    void findCommandById() {
    }

    @Test
    void deleteById() {
    }
}