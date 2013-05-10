package de.java.web;

import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import de.java.domain.OrderState;
import de.java.domain.ReplenishmentOrder;
import de.java.ejb.ReplenishmentOrderService;

@ManagedBean
public class ReplenishmentOrderList {

  @EJB
  private ReplenishmentOrderService orderService;

  private OrderState filterForState = null;

  public Collection<ReplenishmentOrder> getOrders() {
    if (filterForState != null) {
      return orderService.getReplenishmentOrdersInState(filterForState);
    }
    return orderService.getAllReplenishmentOrders();
  }

  public OrderState getFilterForState() {
    return filterForState;
  }

  public void setFilterForState(OrderState filterForState) {
    this.filterForState = filterForState;
  }
}
