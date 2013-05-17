package de.java.domain.orderstate;

import static de.java.domain.OrderState.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import de.java.domain.IllegalOrderStatusTransitionException;
import de.java.domain.OrderState;

public class OrderStateTest {

  @Test public void
  openIsPreceededByPosting() {
    assertThat(OPEN, is(succeededBy(POSTING)));
  }

  @Test public void
  postingIsPreceededByOrdered() {
    assertThat(POSTING, is(succeededBy(ORDERED)));
  }
 
  @Test public void
  orderedIsPreceedByFinished() {
    assertThat(ORDERED, is(succeededBy(FINISHED)));
  }
 
  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  finishedCannotProceedIntoOtherState() {
    FINISHED.getNext();
  }

  @Test(expected=IllegalOrderStatusTransitionException.class) public void
  cancelledCannotProceedIntoOtherState() {
    CANCELLED.getNext();
  }

  private Matcher<OrderState> succeededBy(final OrderState state) {
    return new TypeSafeMatcher<OrderState>() {

      @Override
      public void describeTo(Description description) {
        description.appendText("succeeded by " + state);
      }

      @Override
      public boolean matchesSafely(OrderState initialState) {
        return initialState.getNext() == state;
      }
    };
  }

}
