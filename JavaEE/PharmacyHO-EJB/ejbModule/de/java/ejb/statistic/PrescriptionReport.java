package de.java.ejb.statistic;

import java.io.Serializable;

import de.java.ejb.Subsidiary;

public class PrescriptionReport implements Serializable {

  private static final long serialVersionUID = -2758214314206238350L;
  
  private final Subsidiary subsidiary;
  private final long totalNumberOfPrescriptions;
  private final double averageNumberOfItemsPerPrescription;
  private final Duration averageFulfilmentTimespan;
  
  public PrescriptionReport(Subsidiary subsidiary,
      long totalNumberOfPrescriptions,
      double averageNumberOfItemsPerPrescription,
      Duration averageFulfilmentTimespan) {
    this.subsidiary = subsidiary;
    this.totalNumberOfPrescriptions = totalNumberOfPrescriptions;
    this.averageNumberOfItemsPerPrescription = averageNumberOfItemsPerPrescription;
    this.averageFulfilmentTimespan = averageFulfilmentTimespan;
  }

  public Subsidiary getSubsidiary() {
    return subsidiary;
  }

  public long getTotalNumberOfPrescriptions() {
    return totalNumberOfPrescriptions;
  }

  public double getAverageNumberOfItemsPerPrescription() {
    return averageNumberOfItemsPerPrescription;
  }

  public Duration getAverageFulfilmentTimespan() {
    return averageFulfilmentTimespan;
  }
}
