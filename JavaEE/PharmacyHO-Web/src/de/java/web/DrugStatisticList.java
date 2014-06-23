package de.java.web;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import de.java.ejb.DrugStatisticService;
import de.java.ejb.statistic.AggregatedDrugStatistic;

@ManagedBean
public class DrugStatisticList implements Serializable {

  private static final long serialVersionUID = -6327345569864858043L;

  @EJB
  private DrugStatisticService statisticService;

  private Collection<AggregatedDrugStatistic> statistics;

  public Collection<AggregatedDrugStatistic> getStatistics() {
    if (statistics == null) {
      statistics = statisticService.getAllStatistics();
    }
    return statistics;
  }

  public long getTotalStock() {
    long result = 0;
    for (AggregatedDrugStatistic s : getStatistics()) {
      result += s.getStock();
    }
    return result;
  }

  public long getTotalPendingPositions() {
    long result = 0;
    for (AggregatedDrugStatistic s : getStatistics()) {
      result += s.getPendingPositions();
    }
    return result;
  }

  public long getTotalUnfulfilledItems() {
    long result = 0;
    for (AggregatedDrugStatistic s : getStatistics()) {
      result += s.getUnfulfilledItems();
    }
    return result;
  }
}
