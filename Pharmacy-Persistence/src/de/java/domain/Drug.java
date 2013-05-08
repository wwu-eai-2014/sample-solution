package de.java.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Drug {

  @Id
  private int pzn;

  private String name;

  protected Drug() {
    // default constructor for JPA
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
