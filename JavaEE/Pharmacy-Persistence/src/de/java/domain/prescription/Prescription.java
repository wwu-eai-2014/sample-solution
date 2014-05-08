package de.java.domain.prescription;

import static de.java.domain.prescription.PrescriptionState.ENTRY;
import static javax.persistence.CascadeType.*;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import de.java.domain.customer.Customer;

@Entity
public class Prescription implements Serializable {

  private static final long serialVersionUID = 4728846105423995587L;

  @Id
  @GeneratedValue
  private long id;

  @NotNull
  private PrescriptionState state = ENTRY;

  @ManyToOne(cascade={DETACH,MERGE,PERSIST,REFRESH})
  private Customer customer;

  private String issuer = "";

  @Temporal(TemporalType.DATE)
  private Date issueDate = new Date();

  private Date entryDate = new Date();

  private Date fulfilmentDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public PrescriptionState getState() {
    return state;
  }

  public void setState(PrescriptionState state) {
    this.state = state;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public Date getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(Date issueDate) {
    this.issueDate = issueDate;
  }

  public Date getEntryDate() {
    return entryDate;
  }

  public void setEntryDate(Date entryDate) {
    this.entryDate = entryDate;
  }

  public Date getFulfilmentDate() {
    return fulfilmentDate;
  }

  public void setFulfilmentDate(Date fulfilmentDate) {
    this.fulfilmentDate = fulfilmentDate;
  }

}
