package de.java.ejb;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java.domain.Drug;
import de.java.domain.customer.Customer;
import de.java.domain.prescription.Item;
import de.java.domain.prescription.Prescription;
import de.java.domain.prescription.PrescriptionState;
import de.java.domain.replenishment.OrderState;
import de.java.domain.replenishment.Position;
import de.java.domain.replenishment.ReplenishmentOrder;

@Singleton
@Startup
public class ApplicationInitialiser {

  private static final int PZN_FOR_TEN_ASPIRIN = 1715965;
  private static final int PZN_FOR_SAMPLE_DRUG = 8456716;
  @PersistenceContext
  private EntityManager em;

  @PostConstruct
  public void initialise() {
    if (noDrugsPersisted() && noCustomersPersisted()) {
      populateAppWithSampleDrugs();
      populateAppWithSampleCustomers();
    }
  }

  private boolean noDrugsPersisted() {
    return em.createQuery("FROM Drug").getResultList().size() == 0;
  }

  private boolean noCustomersPersisted() {
    return em.createQuery("FROM Customer").getResultList().size() == 0;
  }

  private void populateAppWithSampleDrugs() {
    final Drug aspirin = new Drug(PZN_FOR_SAMPLE_DRUG, "ASPIRIN PLUS C ORANGE 10St");
    em.persist(aspirin);
    addMoreDrugs();

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_YEAR, -1);
    final Date oneDayAgo = cal.getTime();
    cal.add(Calendar.DAY_OF_YEAR, -1);
    final Date twoDaysAgo = cal.getTime();
    ReplenishmentOrder openOrder = createReplenishmentOrder(OrderState.OPEN);
    em.persist(createPosition(aspirin, 42, openOrder));
    em.persist(openOrder);
    em.persist(createReplenishmentOrder(OrderState.POSTING));
    em.persist(createReplenishmentOrder(OrderState.ORDERED, twoDaysAgo));
    em.persist(createReplenishmentOrder(OrderState.FINISHED, twoDaysAgo, oneDayAgo));
    em.persist(createReplenishmentOrder(OrderState.CANCELLED));
  }

  private void addMoreDrugs() {
    em.persist(new Drug(PZN_FOR_TEN_ASPIRIN, "ASPIRIN PLUS C ORANGE 20St"));
    em.persist(new Drug(451122, "ACC 200 TABS 20St"));
    em.persist(new Drug(451151, "ACC 200 TABS 40St"));
    em.persist(new Drug(451139, "ACC 200 TABS 50St"));
    em.persist(new Drug(451145, "ACC 200 TABS 100St"));
  }

  private Position createPosition(Drug drug, int quantity, ReplenishmentOrder order) {
    final Position position = new Position();
    position.setQuantity(quantity);
    position.setReplenishedDrug(drug);
    position.setOrder(order);
    return position;
  }

  private ReplenishmentOrder createReplenishmentOrder(OrderState state,
      Date expectedDelivery, Date actualDelivery) {
    final ReplenishmentOrder result = createReplenishmentOrder(state, expectedDelivery);
    result.setActualDelivery(actualDelivery);
    return result;
  }

  private ReplenishmentOrder createReplenishmentOrder(OrderState state, Date expectedDelivery) {
    final ReplenishmentOrder result = createReplenishmentOrder(state);
    result.setExpectedDelivery(expectedDelivery);
    return result;
  }

  private ReplenishmentOrder createReplenishmentOrder(OrderState state) {
    final ReplenishmentOrder order = new ReplenishmentOrder();
    order.setState(state);
    return order;
  }

  private void populateAppWithSampleCustomers() {
    Customer albertAmundsen = new Customer();
    albertAmundsen.setName("Albert Amundsen");
    albertAmundsen.setTelephoneNumber("+49 123 456 78");
    em.persist(albertAmundsen);
    
    Prescription fulfilledPrescription = albertAmundsen.createPrescription();
    fulfilledPrescription.setState(PrescriptionState.FULFILLED);
    fulfilledPrescription.setFulfilmentDate(new Date());
    fulfilledPrescription.setIssuer("Dr F. Ulfilled");
    em.persist(fulfilledPrescription);
    
    Prescription prescriptionInFulfilling = albertAmundsen.createPrescription();
    prescriptionInFulfilling.setState(PrescriptionState.FULFILLING);
    prescriptionInFulfilling.setIssuer("Doc Norris");
    em.persist(prescriptionInFulfilling);
    
    Prescription prescriptionInChecking = albertAmundsen.createPrescription();
    prescriptionInChecking.setState(PrescriptionState.CHECKING);
    prescriptionInChecking.setIssuer("Doc Checker");
    em.persist(prescriptionInChecking);
    
    Prescription prescriptionInEntry = albertAmundsen.createPrescription();
    prescriptionInEntry.setIssuer("Doctor Who");
    {
      Drug drug = em.find(Drug.class, PZN_FOR_SAMPLE_DRUG);
      new Item(drug, prescriptionInEntry);
      em.persist(prescriptionInEntry);
    }
    {
      Drug drug = em.find(Drug.class, PZN_FOR_TEN_ASPIRIN);
      new Item(drug, prescriptionInEntry);
      em.persist(prescriptionInEntry);
    }
  }

}
