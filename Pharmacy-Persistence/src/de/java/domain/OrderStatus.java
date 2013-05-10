package de.java.domain;

public enum OrderStatus {
  OPEN {
    public OrderStatus next() { return POSTING; }
    public OrderStatus cancel() { throw new IllegalOrderStatusTransitionException(); }
  },
  POSTING {
    public OrderStatus next() { return ORDERED; }
    public boolean mayBeCancelled() { return true; }
    public OrderStatus cancel() { return CANCELLED; }
  },
  ORDERED {
    public OrderStatus next() { return FINISHED; }
  },
  FINISHED {
    public OrderStatus next() { throw new IllegalOrderStatusTransitionException(); }
    public boolean isPreceedable() { return false; }
  },
  CANCELLED {
    public OrderStatus next() { throw new IllegalOrderStatusTransitionException(); }
    public boolean isPreceedable() { return false; }
  };

  public abstract OrderStatus next();

  public boolean isPreceedable() {
    return true;
  }

  public boolean mayBeCancelled() {
    return false;
  }

  public OrderStatus cancel() {
    throw new IllegalOrderStatusTransitionException();
  }

}
