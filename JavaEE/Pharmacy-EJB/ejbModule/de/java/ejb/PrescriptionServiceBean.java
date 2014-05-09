package de.java.ejb;

import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java.domain.Drug;
import de.java.domain.prescription.Item;
import de.java.domain.prescription.Prescription;
import de.java.domain.prescription.PrescriptionState;

@Stateless
public class PrescriptionServiceBean implements PrescriptionService {

  @PersistenceContext
  private EntityManager em;

  @EJB
  private DrugService drugService;

  @Override
  public Collection<Prescription> getAllPrescriptions() {
    return em.createQuery("FROM Prescription", Prescription.class).getResultList();
  }

  @Override
  public Collection<Prescription> getPrescriptionsInState(PrescriptionState state) {
    final String query = "FROM Prescription p WHERE p.state = :state";
    return em
        .createQuery(query, Prescription.class)
        .setParameter("state", state)
        .getResultList();
  }

  @Override
  public Prescription getPrescription(long id) {
    return em.find(Prescription.class, id);
  }
  
  @Override
  public Prescription getPrescriptionWithItems(long id) {
    return forceLoadOfItems(getPrescription(id));
  }

  private Prescription forceLoadOfItems(Prescription prescription) {
    if (prescription == null) return prescription;
    prescription.getItems().size();
    return prescription;
  }

  @Override
  public void updateEntryData(long id, String issuer, Date issueDate, Date entryDate) {
    Prescription p = getPrescription(id);
    p.setIssuer(issuer);
    p.setIssueDate(issueDate);
    p.setEntryDate(entryDate);
  }

  @Override
  public void cancel(long id) {
    Prescription prescription = getPrescription(id);
    if (prescription.getState().isCancellable()) {
      em.remove(prescription);
    }
  }

  @Override
  public void addNewItem(long prescriptionId, int itemPzn) {
    Prescription p = getPrescription(prescriptionId);
    Drug drug = drugService.getDrug(itemPzn);
    validateDrugNotInPrescription(p, drug);
    
    em.persist(new Item(drug, p));
  }

  private void validateDrugNotInPrescription(Prescription prescription, Drug drug) {
    for (Item item : prescription.getItems()) {
      if (item.getPrescribedDrug().getPzn() == drug.getPzn()) {
        throw new IllegalArgumentException(drug + " already present in this prescription");
      }
    }
  }

  @Override
  public void removeItem(long itemId) {
    Item item = em.find(Item.class, itemId);
    Prescription p = item.getPrescription();

    validateEntryState(p);

    p.getItems().remove(item);
    em.remove(item);
  }

  private void validateEntryState(Prescription p) {
    if (p.getState() != PrescriptionState.ENTRY) {
      throw new IllegalStateException("Cannot remove items from non-ENTRY prescriptions");
    }
  }
  
  @Override
  public void returnToPreviousState(long id) {
    Prescription p = getPrescription(id);
    p.setState(p.getState().getPrevious());
  }

  @Override
  public void proceedToNextState(long id) {
    Prescription p = getPrescription(id);
    p.setState(p.getState().getNext());
  }

}
