package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class RecipeController {

  private final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @RequestMapping("/recipe/show/{id}")
  public String showById(@PathVariable String id, Model model){
    model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
    return "recipe/show";
  }

  @RequestMapping("/recipe/new")
  public String newRecipe(Model model){
    model.addAttribute("recipe", new RecipeCommand());
    return "recipe/recipeform";
  }
  @RequestMapping("/recipe/{id}/update")
  public String update(@PathVariable String id, Model model){
    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
    return "recipe/recipeform";
  }

  //@RequestMapping(name = "recipe", method = RequestMethod.POST)
  @PostMapping
  @RequestMapping("recipe")
  public String saveOrUpdate(@ModelAttribute RecipeCommand command){
    RecipeCommand savedRecipe = recipeService.saveRecipeCommand(command);
    return "redirect:/recipe/show/"+ savedRecipe.getId();
  }

  @GetMapping
  @RequestMapping("recipe/{id}/delete")
  public String deleteById(@PathVariable String id){
    log.debug("delete id "+id);
    recipeService.deleteById(Long.valueOf(id));
    return "redirect:/";
  }

}
