package de.java.domain;

public enum OrderStatus {
  OPEN {
    @Override
    public OrderStatus next() {
      return POSTING;
    }
  }, POSTING {
    @Override
    public OrderStatus next() {
      return ORDERED;
    }
  }, ORDERED {
    @Override
    public OrderStatus next() {
      return FINISHED;
    }
  }, FINISHED {
    @Override
    public OrderStatus next() {
      throw new IllegalOrderStatusTransitionException();
    }
 
    @Override
    public boolean isPreceedable() {
      return false;
    }
  };

  public abstract OrderStatus next();

  public boolean isPreceedable() {
    return true;
  }

  public boolean mayBeCancelled() {
    return false;
  }

}
