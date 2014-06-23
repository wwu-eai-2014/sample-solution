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

  @Override
  public Drug getDrug(int pzn) {
    return em.find(Drug.class, pzn);
  }

  @Override
  public Drug createDrug(Drug newDrug) {
    validateDrugDoesNotExist(newDrug);
    
    // TODO create at subsidiary, too
    return createDrugLocally(newDrug);
  }
  
  private void validateDrugDoesNotExist(Drug newDrug) {
    if (em.createQuery("SELECT COUNT(*) FROM Drug WHERE pzn=:pzn",
        Long.class).setParameter("pzn", newDrug.getPzn())
        .getSingleResult() > 0)
      throw new KeyConstraintViolation(String.format(
          "Drug with PZN: %s already in database", newDrug.getPzn()));
  }
  
  @Override
  public Drug createDrugLocally(Drug newDrug) {
    validateDrugDoesNotExist(newDrug);

    em.persist(newDrug);
    return newDrug;
  }

  @Override
  public Drug updateMasterData(int pzn, String name, String description) {
    // TODO update at subsidiaries
    Drug drug = getDrug(pzn);
    drug.setName(name);
    drug.setDescription(description);
    return drug;
  }
}
