package de.java.web.drug.data;

import java.io.Serializable;

public class DrugDto implements Serializable {

  private static final long serialVersionUID = 1122499804756013078L;

  private int pzn;
  private String name;
  private String description;

  public DrugDto(int pzn, String name, String description) {
    this.pzn = pzn;
    this.name = name;
    this.description = description;
  }

  DrugDto() {
    // for deserialization from JSON
  }

  public int getPzn() {
    return pzn;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

}