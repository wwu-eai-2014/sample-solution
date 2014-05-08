package de.java.domain.prescription;

import de.java.domain.IllegalStatusTransitionException;

public enum PrescriptionState {
  ENTRY {
    public PrescriptionState getNext() { return CHECKING; }
    public boolean isCancellable() { return true; }
  },
  CHECKING {
    public PrescriptionState getNext() { return FULFILLING; }
    public boolean isCancellable() { return true; }
    public boolean isReversible() { return true; }
    public PrescriptionState getPrevious() { return ENTRY; }
  },
  FULFILLING {
    public PrescriptionState getNext() { return FULFILLED; }
  },
  FULFILLED {
    public PrescriptionState getNext() { throw new IllegalStatusTransitionException(); }
    public boolean isProceedable() { return false; }
  };

  public abstract PrescriptionState getNext();

  public boolean isProceedable() { return true; }

  public boolean isCancellable() { return false; }

  public boolean isReversible() { return false; }

  public PrescriptionState getPrevious() { throw new IllegalStatusTransitionException(); }

}
