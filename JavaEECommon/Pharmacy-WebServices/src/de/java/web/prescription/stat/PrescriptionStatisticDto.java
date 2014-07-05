package de.java.web.prescription.stat;

import java.io.Serializable;

public class PrescriptionStatisticDto implements Serializable {

  private static final long serialVersionUID = -4372144523188259425L;

  private long totalNumberOfPrescriptions;
  private double averageNumberOfItemsPerPrescription;
  private long averageFulfilmentTimespan;
  
  public long getTotalNumberOfPrescriptions() {
    return totalNumberOfPrescriptions;
  }
  
  public double getAverageNumberOfItemsPerPrescription() {
    return averageNumberOfItemsPerPrescription;
  }
  
  public long getAverageFulfilmentTimespan() {
    return averageFulfilmentTimespan;
  }
}
