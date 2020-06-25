package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private BigDecimal amount;
  @ManyToOne
  private Recipe recipe;
  @OneToOne(fetch = FetchType.EAGER)
  private UnitOfMeasure unitOfMeasure;

  public Ingredient() {

  }

  public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom)  {
    this.description = description;
    this.amount = amount;
    this.unitOfMeasure = uom;
  }

  public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe)  {
    this.description = description;
    this.amount = amount;
    this.recipe = recipe;
    this.unitOfMeasure = uom;
  }


}
