package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne
  private Recipe recipe;
  @Lob
  private String recipeNotes;

  public Notes() {
  }

  public Notes(Recipe recipe, String recipeNotes) {
    this.recipe = recipe;
    this.recipeNotes = recipeNotes;
  }

  public String getRecipieNotes() {
    return recipeNotes;
  }

  public void setRecipieNotes(String recipieNotes) {
    this.recipeNotes = recipieNotes;
  }
}
