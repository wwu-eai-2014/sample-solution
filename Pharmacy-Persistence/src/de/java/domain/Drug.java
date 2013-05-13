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

  public Drug() {
  }

  public Drug(int pzn, String name) {
    this.pzn = pzn;
    this.name = name;
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

}
