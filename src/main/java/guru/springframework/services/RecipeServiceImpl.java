package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService{

  private final RecipeRepository recipeRepository;

  public RecipeServiceImpl(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  @Override
  public Set<Recipe> getRecipes() {
    log.debug("I am in the service for get recipes");
    Set<Recipe> recipes = new HashSet<>();
    recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
    return recipes;
  }
}
