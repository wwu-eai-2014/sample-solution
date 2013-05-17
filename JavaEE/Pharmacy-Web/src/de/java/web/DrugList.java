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

  private String searchTerm;

  public Collection<Drug> getDrugs() {
    return drugService.getAllDrugsLike(searchTerm);
  }

  public String getSearchTerm() {
    return searchTerm;
  }

  public void setSearchTerm(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  public String search() {
    return "/drug/list.xhtml?faces-redirect=true&search=" + searchTerm;
  }

}
