package de.java.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Drug implements Serializable {

  private static final long serialVersionUID = -9007410528310411219L;

  @Id
  private int pzn;

  @NotNull(message="Name required")
  @Size(min=1, message="Name required")
  private String name;

  private String description;

  public Drug() { }

  @Override
  public String toString() {
    return name + " (PZN: " + pzn + ")";
  }

  public int getPzn() {
    return pzn;
  }

  public void setPzn(int pzn) {
    this.pzn = pzn;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
