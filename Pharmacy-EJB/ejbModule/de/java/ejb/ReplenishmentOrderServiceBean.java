package de.java.ejb;

import static de.java.domain.OrderState.OPEN;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java.domain.Drug;
import de.java.domain.OrderState;
import de.java.domain.Position;
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
  public Collection<Position> getPendingPositions(int pzn) {
    String query = "FROM Position WHERE replenishedDrug.pzn = :pzn AND order.state IN (de.java.domain.OrderState.OPEN, de.java.domain.OrderState.POSTING, de.java.domain.OrderState.ORDERED)";
    return em.createQuery(query, Position.class)
        .setParameter("pzn", pzn)
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
    if (order.getState() == OrderState.FINISHED) {
      // TODO adjust inventory levels
    }
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

  @Override
  public void initiateReplenishment(Drug drug, long quantity) {
    if (hasOpenOrders(drug)) {
      modifyPositionOfOpenOrder(drug, quantity);
    } else {
      addPositionToOpenOrNewOrder(drug, quantity);
    }
  }

  private boolean hasOpenOrders(Drug drug) {
    Collection<Position> pendingPositions = getPendingPositions(drug.getPzn());
    boolean foundOpenOrder = false;
    for (Position position : pendingPositions) {
      if (position.getOrder().getState() == OPEN) {
        foundOpenOrder = true;
        break;
      }
    }
    return foundOpenOrder;
  }

  private void modifyPositionOfOpenOrder(Drug drug, long quantity) {
    for (Position p : getPendingPositions(drug.getPzn())) {
      if (p.getOrder().getState() == OPEN) {
        p.setQuantity(quantity + p.getQuantity());
        break;
      }
    }
  }

  private void addPositionToOpenOrNewOrder(Drug drug, long quantity) {
    Position position = createPosition(drug, quantity);
    if (openOrdersAvailable()) {
      addToOpenOrder(position);
    } else {
      addToNewOrder(position);
    }
  }

  private Position createPosition(Drug drug, long quantity) {
    Position p = new Position();
    p.setQuantity(quantity);
    p.setReplenishedDrug(drug);
    return p;
  }

  private boolean openOrdersAvailable() {
    return !getReplenishmentOrdersInState(OPEN).isEmpty();
  }

  private void addToOpenOrder(Position position) {
    for (ReplenishmentOrder order : getReplenishmentOrdersInState(OPEN)) {
      position.setOrder(order);
      order.addPosition(position);
      em.persist(position);
      break;
    }
  }

  private void addToNewOrder(Position position) {
    ReplenishmentOrder order = new ReplenishmentOrder();
    order.addPosition(position);
    position.setOrder(order);
    em.persist(position);
    em.persist(order);
  }

}
