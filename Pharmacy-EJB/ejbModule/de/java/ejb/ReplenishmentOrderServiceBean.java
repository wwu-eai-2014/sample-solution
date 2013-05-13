package de.java.ejb;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java.domain.OrderState;
import de.java.domain.ReplenishmentOrder;

@Stateless
public class ReplenishmentOrderServiceBean implements ReplenishmentOrderService {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Collection<ReplenishmentOrder> getAllReplenishmentOrders() {
    // ordering is done on the ordinal numbers (not on the state names)
    return em.createQuery("FROM ReplenishmentOrder o ORDER BY o.state", ReplenishmentOrder.class).getResultList();
  }

  @Override
  public Collection<ReplenishmentOrder> getReplenishmentOrdersInState(
      OrderState state) {
    final String query = "FROM ReplenishmentOrder o WHERE o.state = :state";
    return em
        .createQuery(query, ReplenishmentOrder.class)
        .setParameter("state", state)
        .getResultList();
  }

  @Override
  public ReplenishmentOrder getOrder(long id) {
    return em.find(ReplenishmentOrder.class, id);
  }

  @Override
  public void proceedToNextState(long id) {
    ReplenishmentOrder order = getOrder(id);
    order.setState(order.getState().getNext());
  }

  @Override
  public void cancel(long id) {
    ReplenishmentOrder order = getOrder(id);
    order.setState(order.getState().cancel());
    // TODO add positions to existing open order or create new one
  }

  @Override
  public void updateExpectedDeliveryDate(long id, Date expectedDelivery) {
    getOrder(id).setExpectedDelivery(expectedDelivery);
  }

  @Override
  public void updateActualDeliveryDate(long id, Date actualDelivery) {
    getOrder(id).setActualDelivery(actualDelivery);
  }

}
