package altayo.springfw.recipeapp.repositories;

import altayo.springfw.recipeapp.models.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
