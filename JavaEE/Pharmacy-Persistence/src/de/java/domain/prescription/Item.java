package de.java.domain.prescription;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import de.java.domain.Drug;

@Entity
public class Item implements Fulfillable, Serializable {

  private static final long serialVersionUID = -7084419641330262525L;

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne(cascade={})
  @NotNull
  private Drug prescribedDrug;

  @ManyToOne(cascade={CascadeType.ALL})
  @NotNull
  private Prescription prescription;

  Item() {} // default constructor for JPA

  public Item(Drug prescribedDrug, Prescription prescription) {
    this.prescribedDrug = prescribedDrug;
    this.prescription = prescription;
    prescription.getItems().add(this);
  }

  @Override
  public boolean isFulfilled() {
    // TODO Auto-generated method stub
    return false;
  }

  public long getId() {
    return id;
  }

  void setId(long id) {
    this.id = id;
  }

  public Drug getPrescribedDrug() {
    return prescribedDrug;
  }

  void setPrescribedDrug(Drug drug) {
    this.prescribedDrug = drug;
  }

  public Prescription getPrescription() {
    return prescription;
  }

  void setPrescription(Prescription prescription) {
    this.prescription = prescription;
  }

}
