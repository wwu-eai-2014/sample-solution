package de.java.domain;

import static de.java.domain.OrderStatus.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParameterizedOrderStatusTest {
  
  private OrderStatus status;
  private boolean preceedable;
  private boolean mayBeCancelled;

  @Parameters
  public static List<Object[]> data() {
    return Arrays.asList(new Object[][] {
        { OPEN, true, false },
        { POSTING, true, true },
        { ORDERED, true, false },
        { FINISHED, false, false }
    });
  }

  public ParameterizedOrderStatusTest(OrderStatus status,
      boolean preceedable, boolean mayBeCancelled) {
        this.status = status;
        this.preceedable = preceedable;
        this.mayBeCancelled = mayBeCancelled;
  }

  @Test public void
  preceedability() {
    assertEquals(status.isPreceedable(), preceedable);
  }

  @Test public void
  cancelbility() {
    assertEquals(status.mayBeCancelled(), mayBeCancelled);
  }
 
}
