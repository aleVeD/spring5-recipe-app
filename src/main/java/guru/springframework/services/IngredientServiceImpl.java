package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
@Slf4j
public class IngredientServiceImpl implements IngredientService{
  private final RecipeRepository recipeRepository;
  private final IngredientToIngredientCommand ingredientToIngredientCommand;

  public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
    this.recipeRepository = recipeRepository;
    this.ingredientToIngredientCommand = ingredientToIngredientCommand;
  }

  @Override
  public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
    Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
    if(!optionalRecipe.isPresent()){
      log.error(" no existe receta");
    }

    Recipe recipe = optionalRecipe.get();
    Optional<IngredientCommand> optionalIngredientCommand =
            recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(id))
            .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
    if(!optionalIngredientCommand.isPresent()){
      log.error("no existe ingrediente");
    }

    return optionalIngredientCommand.get();

  }
}
