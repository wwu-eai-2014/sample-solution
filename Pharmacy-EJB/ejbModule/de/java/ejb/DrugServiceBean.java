package de.java.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.java.domain.Drug;

@Stateless
public class DrugServiceBean implements DrugService {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Collection<Drug> getAllDrugs() {
    return em.createQuery("FROM Drug", Drug.class).getResultList();
  }

  @Override
  public Collection<Drug> getAllDrugsLike(String searchTerm) {
    if (empty(searchTerm)) {
      return getAllDrugs();
    }

    final String query = "SELECT d FROM Drug d WHERE d.pzn = :pzn OR lower(d.name) LIKE :searchTerm";
    return em.createQuery(query, Drug.class)
        .setParameter("searchTerm", prepareUniversalMatch(searchTerm))
        .setParameter("pzn", parseIntOrDefaultToZero(searchTerm))
        .getResultList();
  }

  private boolean empty(String searchTerm) {
    return searchTerm == null || searchTerm.trim().isEmpty();
  }

  private int parseIntOrDefaultToZero(String searchTerm) {
    try {
      return Integer.parseInt(searchTerm);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  private String prepareUniversalMatch(String searchTerm) {
    return "%" + searchTerm.toLowerCase() + "%";
  }
}
