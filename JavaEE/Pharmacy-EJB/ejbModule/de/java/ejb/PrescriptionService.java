package de.java.ejb;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Remote;

import de.java.domain.prescription.Item;
import de.java.domain.prescription.Prescription;
import de.java.domain.prescription.PrescriptionState;

@Remote
public interface PrescriptionService {

  Collection<Prescription> getAllPrescriptions();
  
  Collection<Prescription> getPrescriptionsInState(
      PrescriptionState filterForState);

  Prescription getPrescription(long id);
  
  Prescription getPrescriptionWithItems(long id);

  void updateEntryData(long id, String issuer, Date issueDate, Date entryDate);

  void cancel(long id);

  void addNewItem(long prescriptionId, int itemPzn);

  void removeItem(long itemId);

}
