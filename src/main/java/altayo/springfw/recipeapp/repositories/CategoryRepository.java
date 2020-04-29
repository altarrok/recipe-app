package altayo.springfw.recipeapp.repositories;

import altayo.springfw.recipeapp.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
