package de.java.web;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java.domain.Drug;

@ManagedBean
public class DrugList {
  
  @PersistenceContext
  private EntityManager em;

  public Collection<Drug> getDrugs() {
    return em.createQuery("FROM Drug", Drug.class).getResultList();
  }
  
}
