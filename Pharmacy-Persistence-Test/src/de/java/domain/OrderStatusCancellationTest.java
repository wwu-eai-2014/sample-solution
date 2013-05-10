package de.java.domain;

import static de.java.domain.OrderStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class OrderStatusCancellationTest {
  @Test public void
  postingCancellingLeadsToCancelled() {
    assertThat(POSTING.cancel(), is(CANCELLED));
  }

  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  openCannotProceedIntoCancelled() {
    OPEN.cancel();
  }

  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  orderedCannotProceedIntoCancelled() {
    ORDERED.cancel();
  }

  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  finishedCannotProceedIntoCancelled() {
    FINISHED.cancel();
  }

  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  cancelledCannotBeCancelled() {
    CANCELLED.cancel();
  }
}
