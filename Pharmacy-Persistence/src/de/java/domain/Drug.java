package de.java.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Drug {

	@Id
	private int pzn;
   
	private String name;

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
