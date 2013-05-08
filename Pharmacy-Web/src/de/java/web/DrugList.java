package de.java.web;

import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import de.java.domain.Drug;
import de.java.ejb.DrugService;

@ManagedBean
public class DrugList {

  @EJB
  private DrugService drugService;
  

  public Collection<Drug> getDrugs() {
    return drugService.getAllDrugs();
  }
  
}
