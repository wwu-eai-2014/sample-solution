package de.java.ejb.statistic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.java.domain.Drug;

public class AggregatedDrugStatistic implements Serializable {

  private static final long serialVersionUID = 4198294438740064593L;

  private final Drug drug;
  private final Collection<IndividualDrugStatistic> stats = new ArrayList<>();

  public AggregatedDrugStatistic (Drug drug) {
    this.drug = drug;
  }

  public void addIndividualStatistic(IndividualDrugStatistic individualStatistic) {
    stats.add(individualStatistic);
  }

  public Drug getDrug() {
    return drug;
  }

  public Collection<IndividualDrugStatistic> getIndividualStatistics() {
    return Collections.unmodifiableCollection(stats);
  }

  public long getStock() {
    long result = 0;
    for (IndividualDrugStatistic i : stats) {
      result += i.getStock();
    }
    return result;
  }

  public long getMinimumInventoryLevel() {
    long result = 0;
    for (IndividualDrugStatistic i : stats) {
      result += i.getMinimumInventoryLevel();
    }
    return result;
  }

  public long getOptimalInventoryLevel() {
    long result = 0;
    for (IndividualDrugStatistic i : stats) {
      result += i.getOptimalInventoryLevel();
    }
    return result;
  }

  public long getPendingPositions() {
    long result = 0;
    for (IndividualDrugStatistic i : stats) {
      result += i.getPendingPositions();
    }
    return result;
  }

  public long getUnfulfilledItems() {
    long result = 0;
    for (IndividualDrugStatistic i : stats) {
      result += i.getUnfulfilledItems();
    }
    return result;
  }

}
