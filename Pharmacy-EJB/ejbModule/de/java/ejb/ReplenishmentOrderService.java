package de.java.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.java.domain.OrderState;
import de.java.domain.ReplenishmentOrder;

@Remote
public interface ReplenishmentOrderService {

  Collection<ReplenishmentOrder> getAllReplenishmentOrders();

  Collection<ReplenishmentOrder> getReplenishmentOrdersInState(
      OrderState filterForState);

  ReplenishmentOrder getOrder(long id);

}
