package de.java.web;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import de.java.domain.prescription.Prescription;
import de.java.ejb.PrescriptionService;

@ManagedBean
public class PrescriptionList implements Serializable {

  private static final long serialVersionUID = -8573278652619706897L;

  @EJB
  private PrescriptionService prescriptionService;

  public Collection<Prescription> getPrescriptions() {
    return prescriptionService.getAllPrescriptions();
  }

}
