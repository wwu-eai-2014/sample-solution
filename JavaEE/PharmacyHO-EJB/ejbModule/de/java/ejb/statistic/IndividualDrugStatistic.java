package de.java.ejb.statistic;

import java.io.Serializable;

import de.java.ejb.Subsidiary;
import de.java.web.drug.stat.DrugStatisticDto;

public class IndividualDrugStatistic implements Serializable {

  private static final long serialVersionUID = -6745571973390206781L;

  private final Subsidiary source;
  private final long stock;
  private final long minimumInventoryLevel;
  private final long optimalInventoryLevel;
  private final long pendingPositions;
  private final long unfulfilledItems;

  public IndividualDrugStatistic(Subsidiary source, DrugStatisticDto drugStatistic) {
    this.source = source;
    stock = drugStatistic.getStock();
    minimumInventoryLevel = drugStatistic.getMinimumInventoryLevel();
    optimalInventoryLevel = drugStatistic.getOptimalInventoryLevel();
    pendingPositions = drugStatistic.getPendingPositions();
    unfulfilledItems = drugStatistic.getUnfulfilledItems();
  }

  public Subsidiary getSource() {
    return source;
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
