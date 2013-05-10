package de.java.domain;

import static de.java.domain.OrderStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

public class OrderStatusTest {

  @Test public void
  openIsPreceededByPosting() {
    assertThat(OPEN, is(preceededBy(POSTING)));
  }

  @Test public void
  postingIsPreceededByOrdered() {
    assertThat(POSTING, is(preceededBy(ORDERED)));
  }
 
  @Test public void
  orderedIsPreceedByFinished() {
    assertThat(ORDERED, is(preceededBy(FINISHED)));
  }
 
  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  finishedCannotProceedIntoOtherStatus() {
    FINISHED.next();
  }

  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  cancelledCannotProceedIntoOtherStatus() {
    CANCELLED.next();
  }

  private Matcher<OrderStatus> preceededBy(final OrderStatus status) {
    return new TypeSafeMatcher<OrderStatus>() {

      @Override
      public void describeTo(Description description) {
        description.appendText("preceeded by " + status);
      }

      @Override
      public boolean matchesSafely(OrderStatus initialStatus) {
        return initialStatus.next() == status;
      }
    };
  }

}
