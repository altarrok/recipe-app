package altayo.springfw.recipeapp.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 4L;
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @Test
    void getDescription() {
        String descValue = "Description";
        category.setDescription(descValue);
        assertEquals(descValue, category.getDescription());
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        recipe1.setId(1L);
        recipe2.setId(2L);

        category.getRecipes().add(recipe1);
        category.getRecipes().add(recipe2);

        assertEquals(2, category.getRecipes().size());
    }
}