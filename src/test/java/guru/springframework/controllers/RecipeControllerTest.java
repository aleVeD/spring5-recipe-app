package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class RecipeControllerTest {
  @Mock
  RecipeService recipeService;

  RecipeController recipeController;
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    recipeController = new RecipeController(recipeService);
    mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
  }

  @Test
  void showById() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId(1L);

    when(recipeService.findById(anyLong())).thenReturn(recipe);
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/show"))
            .andExpect(model().attributeExists("recipe"));
  }

  @Test
  void newRecipe() throws Exception {
    RecipeCommand command = new RecipeCommand();
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/recipeform"))
            .andExpect(model().attributeExists("recipe"));
  }

  @Test
  void save() throws Exception {
    RecipeCommand command = new RecipeCommand();
    command.setId(22L);
    when(recipeService.saveRecipeCommand(any())).thenReturn(command);
    mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some string"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/show/22"));
  }

  @Test
  void update() throws Exception {
    RecipeCommand command = new RecipeCommand();
    command.setId(3L);
    when(recipeService.findCommandById(anyLong())).thenReturn(command);
    mockMvc.perform(MockMvcRequestBuilders.get("/recipe/3/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/recipeform"))
            .andExpect(model().attributeExists("recipe"));
  }

    @Test
    void deleteByIdControllerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
        verify(recipeService, times(1)).deleteById(1L);
    }
    

}