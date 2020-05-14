package altayo.springfw.recipeapp.repositories;

import altayo.springfw.recipeapp.models.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
