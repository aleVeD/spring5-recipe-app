package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

  @Mock
  RecipeService recipeService;

  IngredientController controller;
  MockMvc mockMvc;
  @Mock
  IngredientService ingredientService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    controller = new IngredientController( recipeService, ingredientService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void testListIngredients() throws Exception {
    //given
    RecipeCommand recipeCommand = new RecipeCommand();
    System.out.println(recipeCommand);
    recipeCommand.setId(2L);
    System.out.println(recipeService.findCommandById(anyLong()).getId());
    when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

    //when
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/ingredient/list"))
            .andExpect(model().attributeExists("recipe"));

    //then
    verify(recipeService, times(1)).findCommandById(anyLong());
  }

  @Test
  public void showIngredientTest() throws Exception {
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(1L);
    System.out.println(ingredientCommand);
    when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/show"))
    .andExpect(status().isOk())
    .andExpect(view().name("recipe/ingredient/show"))
    .andExpect(model().attributeExists("ingredient"));
  }
}