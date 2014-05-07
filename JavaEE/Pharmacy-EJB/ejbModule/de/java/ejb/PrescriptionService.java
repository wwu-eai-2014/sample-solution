package de.java.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.java.domain.prescription.Prescription;

@Remote
public interface PrescriptionService {

  Collection<Prescription> getAllPrescriptions();

}
