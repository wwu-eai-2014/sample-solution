package de.java.web.drug.stat;

import java.io.Serializable;

public class DrugStatisticDto implements Serializable {

  private static final long serialVersionUID = -6359301726733749770L;

  private int pzn;
  private long stock;
  private long minimumInventoryLevel;
  private long optimalInventoryLevel;
  private long pendingPositions;
  private long unfulfilledItems;

  public DrugStatisticDto(int pzn, long stock,
      long minimumInventoryLevel, long optimalInventoryLevel,
      long pendingPositions, long unfulfilledItems) {
    this.pzn = pzn;
    this.stock = stock;
    this.minimumInventoryLevel = minimumInventoryLevel;
    this.optimalInventoryLevel = optimalInventoryLevel;
    this.pendingPositions = pendingPositions;
    this.unfulfilledItems = unfulfilledItems;
  }

  DrugStatisticDto() {
    // for deserialization from JSON
  }

  public int getPzn() {
    return pzn;
  }

  public long getStock() {
    return stock;
  }

  public long getMinimumInventoryLevel() {
    return minimumInventoryLevel;
  }

  public long getOptimalInventoryLevel() {
    return optimalInventoryLevel;
  }

  public long getPendingPositions() {
    return pendingPositions;
  }

  public long getUnfulfilledItems() {
    return unfulfilledItems;
  }

}
