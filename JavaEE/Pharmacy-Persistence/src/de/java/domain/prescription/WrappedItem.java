package de.java.domain.prescription;

import java.io.Serializable;

import de.java.domain.Drug;

public class WrappedItem implements Serializable {

  private static final long serialVersionUID = -4955900242712940679L;

  private final Item item;

  private final long quantityPending;

  public WrappedItem(Item item, long quantityPending) {
    this.item = item;
    this.quantityPending = quantityPending;
  }

  public Item getItem() {
    return item;
  }

  public FulfilmentState getState() {
    return item.getState();
  }

  public Drug getPrescribedDrug() {
    return item.getPrescribedDrug();
  }

  public long getQuantityPending() {
    return quantityPending;
  }

  public long getQuantityRequired() {
    // TODO use proper value
    return 0;
  }
}
