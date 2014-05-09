package de.java.web;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.java.domain.prescription.Prescription;
import de.java.ejb.PrescriptionService;
import de.java.web.util.Util;

@ManagedBean
@ViewScoped
public class PrescriptionPage implements Serializable {

  private static final long serialVersionUID = -1321629547690677910L;

  @EJB
  private PrescriptionService prescriptionService;

  private long id;

  private Prescription prescription;

  private Date fulfilmentDate; 

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
    init();
  }

  public void init() {
    prescription = null;
  }

  public void ensureInitialized(){
    try{
      if(getPrescription() != null)
        // Success
        return;
    } catch(EJBException e) {
      e.printStackTrace();
    }
    Util.redirectToRoot();
  }

  public String update() {
    prescriptionService.updateEntryData(prescription.getId(),
        prescription.getIssuer(),
        prescription.getIssueDate(),
        prescription.getEntryDate());
    return toPrescriptionPage();
  }
  
  private String toPrescriptionPage() {
    return "details.xhtml?faces-redirect=true&id=" + id;
  }

  public String cancel() {
    prescriptionService.cancel(prescription.getId());
    return toPrescriptionList();
  }

  private String toPrescriptionList() {
    return "list.xhtml";
  }

  public Prescription getPrescription() {
    if (prescription == null) {
      prescription = prescriptionService.getPrescription(id);
    }
    return prescription;
  }

  public Date getFulfilmentDate() {
    return fulfilmentDate;
  }

  public void setFulfilmentDate(Date fulfilmentDate) {
    this.fulfilmentDate = fulfilmentDate;
  }

}
