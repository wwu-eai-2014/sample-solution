package de.java.domain;

public enum OrderState {
  OPEN {
    public OrderState next() { return POSTING; }
  },
  POSTING {
    public OrderState next() { return ORDERED; }
    public boolean mayBeCancelled() { return true; }
    public OrderState cancel() { return CANCELLED; }
  },
  ORDERED {
    public OrderState next() { return FINISHED; }
  },
  FINISHED {
    public OrderState next() { throw new IllegalOrderStatusTransitionException(); }
    public boolean isProceedable() { return false; }
  },
  CANCELLED {
    public OrderState next() { throw new IllegalOrderStatusTransitionException(); }
    public boolean isProceedable() { return false; }
  };

  public abstract OrderState next();

  public boolean isProceedable() {
    return true;
  }

  public boolean mayBeCancelled() {
    return false;
  }

  public OrderState cancel() {
    throw new IllegalOrderStatusTransitionException();
  }

}
