package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final CategoryRepository categoryRepository;
  private final UnitOfMeasureRepository unitOfMeasureRepository;
  private final RecipeRepository recipeRepository;

  public RecipeBootstrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
    this.categoryRepository = categoryRepository;
    this.unitOfMeasureRepository = unitOfMeasureRepository;
    this.recipeRepository = recipeRepository;
  }



  private List<Recipe> getRecipes(){
    List<Recipe> recipes = new ArrayList<>(2);
    Optional<UnitOfMeasure> uomCup = unitOfMeasureRepository.findByDescription("Cup");

    if(!uomCup.isPresent()){
      throw new RuntimeException("Cup not found");
    }

    Optional<UnitOfMeasure> uomTable = unitOfMeasureRepository.findByDescription("Tablespoon");

    if(!uomTable.isPresent()){
      throw  new RuntimeException("tablespon not found");
    }


    Optional<UnitOfMeasure> uomDash = unitOfMeasureRepository.findByDescription("Dash");

    if(!uomDash.isPresent()){
      throw  new RuntimeException("Dash not found");
    }


    Optional<UnitOfMeasure> uomPin = unitOfMeasureRepository.findByDescription("Pin");

    if(!uomPin.isPresent()){
      throw  new RuntimeException("Pin not found");
    }

    Optional<UnitOfMeasure> uomTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");

    if(!uomTeaspoon.isPresent()){
      throw  new RuntimeException("Teaspoon not found");
    }

    UnitOfMeasure cupUom = uomCup.get();
    UnitOfMeasure pinUom = uomPin.get();
    UnitOfMeasure dashUom = uomDash.get();
    UnitOfMeasure tableUom = uomTable.get();
    UnitOfMeasure uommTeaspoon = uomTeaspoon.get();

    Optional<Category> vegetarian = categoryRepository.findByDescription("Vegetarian");
    if(!vegetarian.isPresent()){
      throw new RuntimeException("vegetarian food is not present");
    }

    Optional<Category> mexican = categoryRepository.findByDescription("Mexican");
    if(!mexican.isPresent()){
      throw new RuntimeException("Mexican food is not present");
    }

    Category catVet = vegetarian.get();
    Category catMex = mexican.get();
    Recipe spinachFrittata = new Recipe();
    spinachFrittata.setDescription("Quick and easy spinach frittata recipe with eggs, fresh spinach, onions, garlic, Parmesan and goat cheese, and sun-dried tomatoes.");
    spinachFrittata.setCookTime(4);
    spinachFrittata.setPrepTime(12);
    spinachFrittata.setDifficulty(Difficulty.EASY);
    spinachFrittata.setDirections("1 Whisk together eggs, milk, Parm, salt, pepper: In a mixing bowl, whisk together eggs, milk, and Parmesan cheese. Stir in the salt and pepper. Set aside.\n" +
            "\n" +
            "2 Sauté onions, garlic: Heat olive olive oil in an oven-proof, stick-free skillet on medium heat. Add the onions and sauté until translucent, about 4-5 minutes. Add the garlic and sun-dried tomatoes (if using) and cook a minute more.\n" +
            "\n" +
            "3 Add the chopped spinach, a handful at a time, use tongs to mix with the onions. As the fresh spinach begins to wilt and there is more room in the pan, add more of the fresh chopped spinach to the pan.\n" +
            " \n" +
            "4 Add the egg mixture: Once the spinach has wilted, spread the mixture out evenly on bottom of the pan. Pour the egg Parmesan mixture over the spinach and onions.\n" +
            " \n" +
            "Use a spatula to lift up the spinach mixture along the sides of the pan to let egg mixture flow underneath.\n" +
            "5 Sprinkle bits of goat cheese over the top of the frittata mixture.\n" +
            "\n" +
            "6 Lower heat, cover and cook: Lower the heat to low and cover the pan. Let cook on the stovetop 10 to 13 minutes, until all but the center of the frittata is set. (You may need to check a few times, to see how well the frittata is setting.) The center should still be wiggly. Pre-heat the broiler.\n" +
            "\n" +
            "7 Finish under the broiler: Set the oven rack in the top third of the oven. Broil for 3 to 4 minutes until the top is golden. Remove from oven with oven mitts and let cool for several minutes.\n" +
            "\n");
    Notes note = new Notes();
    note.setRecipeNotes("Although the pan may be out of the oven for a few minutes, the handle is still very hot! To keep from accidentally picking it up by the handle while hot (speaking from experience, ouch!) take a piece of ice and melt it against the pan's handle to cool it down.");
    note.setRecipe(spinachFrittata);
    spinachFrittata.setNotes(note);
    spinachFrittata.addIngredient(new Ingredient("large eggs", new BigDecimal(9), dashUom));
    spinachFrittata.addIngredient(new Ingredient("Milk", new BigDecimal(2), cupUom));
    spinachFrittata.addIngredient(new Ingredient("grated Parmesan cheese", new BigDecimal(1/3), cupUom));
    spinachFrittata.addIngredient(new Ingredient("salt", new BigDecimal(1/4), uommTeaspoon));
    spinachFrittata.addIngredient(new Ingredient("freshly ground pepper", new BigDecimal(1/4), uommTeaspoon));
    spinachFrittata.getCategories().add(catVet);
    spinachFrittata.getCategories().add(catMex);
    recipes.add(spinachFrittata);
    log.debug("I added the first recipe");

    //*****************************************************

    //other recipe
    Recipe tacosRecipe = new Recipe();
    tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
    tacosRecipe.setCookTime(9);
    tacosRecipe.setPrepTime(20);
    tacosRecipe.setDifficulty(Difficulty.MODERATE);

    tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
            "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
            "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
            "\n" +
            "\n" +
            "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
            "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
            "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
            "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
            "\n" +
            "\n" +
            "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

    Notes tacoNotes = new Notes();
    tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
            "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
            "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
            "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
            "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
            "\n" +
            "\n" +
            "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
    //tacoNotes.setRecipe(tacosRecipe);

    tacosRecipe.setNotes(tacoNotes);


    tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableUom));
    tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), uommTeaspoon));
    tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), uommTeaspoon));
    tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), uommTeaspoon));
    tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), uommTeaspoon));
    tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), cupUom));
    tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableUom));
    tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableUom));
    tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tableUom));
    tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableUom));
    tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), dashUom));
    tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupUom));
    tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), dashUom));
    tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), dashUom));
    tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pinUom));
    tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), dashUom));
    tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), dashUom));
    tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupUom));
    tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), dashUom));

    tacosRecipe.getCategories().add(catVet);
    tacosRecipe.getCategories().add(catMex);

    recipes.add(tacosRecipe);
    return recipes;
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent context) {
    recipeRepository.saveAll(getRecipes());
  }
}
