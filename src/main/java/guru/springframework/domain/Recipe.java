package guru.springframework.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Getter
@Setter
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer serving;
  private String source;
  private String url;
  @Lob
  private String directions;
  @ManyToMany
  @JoinTable(name = "recipe_category",
             joinColumns = @JoinColumn( name= "recipe_id"),
              inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  @Enumerated(value = EnumType.STRING)
  private Difficulty difficulty;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
  private  Set<Ingredient> ingredients = new HashSet<>();

  @Lob
  private Byte[] image;

  @OneToOne(cascade = CascadeType.ALL)
  private Notes notes;

  public void setNotes(Notes notes) {
    this.notes = notes;
//    notes.setRecipe(this);
  }

  public Recipe addIngredient(Ingredient ingredient){
    ingredient.setRecipe(this);
    this.ingredients.add(ingredient);
    return this;
  }

}