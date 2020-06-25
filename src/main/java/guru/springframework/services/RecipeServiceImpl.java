package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService{

  private final RecipeRepository recipeRepository;
  private final RecipeToRecipeCommand recipeToRecipeCommand;
  private final RecipeCommandToRecipe recipeCommandToRecipe;

  public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
    this.recipeRepository = recipeRepository;
    this.recipeToRecipeCommand = recipeToRecipeCommand;


    this.recipeCommandToRecipe = recipeCommandToRecipe;
  }



  @Override
  public Set<Recipe> getRecipes() {
    log.debug("I am in the service for get recipes");
    Set<Recipe> recipes = new HashSet<>();
    recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
    return recipes;
  }

  @Override
  public Recipe findById(Long l) {

    Optional<Recipe> recipeOptional = recipeRepository.findById(l);

    if (!recipeOptional.isPresent()) {
      throw new RuntimeException("Recipe Not Found!");
    }

    return recipeOptional.get();
  }

  @Override
  @Transactional
  public RecipeCommand findCommandById(Long l) {
    return recipeToRecipeCommand.convert(findById(l));
  }

  @Override
  public void deleteById(Long id) {
    recipeRepository.deleteById(id);
  }

  @Override
  @Transactional
  public RecipeCommand saveRecipeCommand(RecipeCommand command){
    Recipe detached = recipeCommandToRecipe.convert(command);
    Recipe recipeSaved = recipeRepository.save(detached);
    log.debug("recipe saved!! "+ recipeSaved.getId());
    return recipeToRecipeCommand.convert(recipeSaved);
  }
}
