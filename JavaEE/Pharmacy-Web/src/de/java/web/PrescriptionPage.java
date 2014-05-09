package de.java.web;

import static de.java.web.util.Util.errorMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import de.java.domain.Drug;
import de.java.domain.prescription.Fulfillable;
import de.java.domain.prescription.Item;
import de.java.domain.prescription.Prescription;
import de.java.ejb.DrugService;
import de.java.ejb.PrescriptionService;
import de.java.web.util.Util;

@ManagedBean
@ViewScoped
public class PrescriptionPage implements Serializable {

  private static final long serialVersionUID = -1321629547690677910L;

  @EJB
  private PrescriptionService prescriptionService;

  @EJB
  private DrugService drugService;

  private long id;
  private Prescription prescription;

  private int newPzn;
  private Drug newItemDrug;

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

  public String returnToPrevious() {
    prescriptionService.returnToPreviousState(getPrescription().getId());
    return toPrescriptionPage();
  }

  public boolean isProceedable() {
    Collection<Fulfillable> fulfillables = new ArrayList<>();
    fulfillables.addAll(getPrescription().getItems());
    return getPrescription().getState().isProceedable(fulfillables);
  }

  public String proceed() {
    prescriptionService.proceedToNextState(getPrescription().getId());
    return toPrescriptionPage();
  }

  public String cancel() {
    prescriptionService.cancel(prescription.getId());
    return toPrescriptionList();
  }

  private String toPrescriptionList() {
    return "list.xhtml";
  }

  public String remove(Item item) {
    prescriptionService.removeItem(item.getId());
    return toPrescriptionPage();
  }

  public void addNewItem() {
    try {
      prescriptionService.addNewItem(getPrescription().getId(), getNewPzn());
    } catch (EJBException e) {
      String msg = Util.getCausingMessage(e);
      FacesContext.getCurrentInstance().addMessage(null, errorMessage(msg));
    }
    setNewPzn(0);
    prescription = null;
  }

  public Prescription getPrescription() {
    if (prescription == null) {
      prescription = prescriptionService.getPrescriptionWithItems(id);
    }
    return prescription;
  }
  
  public int getNewPzn() {
    return newPzn;
  }
  
  public void setNewPzn(int newPzn) {
    this.newPzn = newPzn;
    newItemDrug = null;
  }
  
  public Drug getNewItemDrug() {
    if (newItemDrug == null) {
      try {
        newItemDrug = drugService.getDrug(getNewPzn());
      } catch (EJBException e) {
        if (!(e.getCausedByException() instanceof NoResultException)) {
          throw e;
        }
      }
    }
    return newItemDrug;
  }

  public Date getFulfilmentDate() {
    return fulfilmentDate;
  }

  public void setFulfilmentDate(Date fulfilmentDate) {
    this.fulfilmentDate = fulfilmentDate;
  }

}
