package guru.springframework.domain;

import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
  Category category;

  @BeforeEach
  public void setUp() throws Exception{
    category = new Category();
    System.out.println("here in before");

  }

  @Test
  void getId(){
    Long idValue = 4L;
    category.setId(idValue);
    assertEquals(idValue, category.getId());
  }

  @Test
  void getDescription() {
    category.setDescription("Mexican");
  }

  @Test
  void getRecipes() {
  }

}