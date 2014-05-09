package de.java.ejb;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java.domain.prescription.Prescription;
import de.java.domain.prescription.PrescriptionState;

@Stateless
public class PrescriptionServiceBean implements PrescriptionService {

  @PersistenceContext
  private EntityManager em;

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

}
