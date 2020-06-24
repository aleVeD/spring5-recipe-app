package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;

  @Mock
  RecipeRepository recipeRepository;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    recipeService = new RecipeServiceImpl(recipeRepository);
  }

  @Test
  void getRecipes() throws Exception{
    Recipe recipe = new Recipe();
    HashSet recipe2 = new HashSet();
    recipe2.add(recipe);
    System.out.println("recipe2 = " + recipe2);
    when(recipeRepository.findAll()).thenReturn(recipe2);
    Set<Recipe> recipes = recipeService.getRecipes();
    assertEquals(recipes.size(), 1);
    verify(recipeRepository, times(1)).findAll();
  }

  @Test
  void findById() {
    Recipe recipe = new Recipe();
    recipe.setId(45L);
    Optional<Recipe> setRecipes = Optional.of(recipe);
    when(recipeRepository.findById(anyLong())).thenReturn(setRecipes);
    Recipe recipeReturned = recipeService.findById(45L);
    assertNotNull(recipeReturned);
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, never()).findAll();
  }
}