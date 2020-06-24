package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import javax.naming.ldap.Control;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {
  @Mock
  RecipeService recipeService;

  @Mock
  Model model;

  IndexController indexController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    indexController = new IndexController(recipeService);
  }

  @Test
  void getIndexPage() {
    Set<Recipe> recipes = new HashSet<>();
    recipes.add(new Recipe());
    Recipe r = new Recipe();
    r.setId(11L);
    recipes.add(r);
    Recipe l = new Recipe();
    l.setId(12L);
    recipes.add(l);
    recipes.add(new Recipe());
    when(recipeService.getRecipes()).thenReturn(recipes);
    ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
    String page = "index";
    String viewName = indexController.getIndexPage(model);
    assertEquals(page, viewName);
    verify(recipeService, times(1)).getRecipes();
    verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
    Set<Recipe> inController = argumentCaptor.getValue();
    assertEquals(3, inController.size());
  }

  @Test
  void testMockMVC() throws Exception {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
  }

}