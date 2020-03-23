package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class Notes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne
  private Recipie recipe;
  @Lob
  private String recipieNotes;

  public Recipie getRecipe() {
    return recipe;
  }



  public void setRecipe(Recipie recipe) {
    this.recipe = recipe;
  }

  public String getRecipieNotes() {
    return recipieNotes;
  }

  public void setRecipieNotes(String recipieNotes) {
    this.recipieNotes = recipieNotes;
  }
}
