package de.java.ejb;

import java.util.Collection;

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
    return em.createQuery("FROM ReplenishmentOrder", ReplenishmentOrder.class).getResultList();
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

}
