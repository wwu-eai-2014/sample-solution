package de.java.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java.domain.Drug;
import de.java.domain.OrderState;
import de.java.domain.ReplenishmentOrder;

@Singleton
@Startup
public class ApplicationInitialiser {

  @PersistenceContext
  private EntityManager em;

  @PostConstruct
  public void initialise() {
    if (noDrugsPersisted()) {
      populateAppWithSampleDrugs();
    }
  }

  private boolean noDrugsPersisted() {
    return em.createQuery("FROM Drug").getResultList().size() == 0;
  }

  private void populateAppWithSampleDrugs() {
    em.persist(new Drug(8456716, "ASPIRIN PLUS C ORANGE 10St"));
    em.persist(new Drug(1715965, "ASPIRIN PLUS C ORANGE 20St"));
    em.persist(new Drug(451122, "ACC 200 TABS 20St"));
    em.persist(new Drug(451139, "ACC 200 TABS 50St"));
    em.persist(new Drug(451145, "ACC 200 TABS 100St"));
    em.persist(new Drug(451151, "ACC 200 TABS 100St"));

    em.persist(createReplenishmentOrder(OrderState.OPEN));
    em.persist(createReplenishmentOrder(OrderState.POSTING));
    em.persist(createReplenishmentOrder(OrderState.ORDERED));
    em.persist(createReplenishmentOrder(OrderState.FINISHED));
    em.persist(createReplenishmentOrder(OrderState.CANCELLED));
    em.persist(createReplenishmentOrder(OrderState.OPEN));
  }

  private ReplenishmentOrder createReplenishmentOrder(OrderState state) {
    ReplenishmentOrder order = new ReplenishmentOrder();
    order.setState(state);
    return order;
  }
}
