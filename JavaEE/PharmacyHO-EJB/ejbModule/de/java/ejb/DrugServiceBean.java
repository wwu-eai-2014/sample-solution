package de.java.ejb;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import de.java.domain.Drug;
import de.java.ejb.jms.JmsDrugService;
import de.java.web.drug.data.DrugDto;
import de.java.web.drug.data.DrugResource;

@Stateless
public class DrugServiceBean implements DrugService {

  @EJB
  JmsDrugService jmsService;

  @PersistenceContext
  private EntityManager em;

  @PostConstruct
  public void registerRestEasy() {
    RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
  }

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
    jmsService.createAtSubsidiaries(newDrug);
    DrugResource drugResource = createResourceFor(Subsidiary.C_SHARPE);
    drugResource.createDrug(createDto(newDrug));
    return createDrugLocally(newDrug);
  }

  private void validateDrugDoesNotExist(Drug newDrug) {
    if (em.createQuery("SELECT COUNT(*) FROM Drug WHERE pzn=:pzn",
        Long.class).setParameter("pzn", newDrug.getPzn())
        .getSingleResult() > 0)
      throw new KeyConstraintViolation(String.format(
          "Drug with PZN: %s already in database", newDrug.getPzn()));
  }

  private DrugResource createResourceFor(Subsidiary subsidiary) {
    return new ResteasyClientBuilder().build().target(subsidiary.getBaseUri()).proxy(DrugResource.class);
  }

  private DrugDto createDto(Drug drug) {
    return new DrugDto(drug.getPzn(), drug.getName(), drug.getDescription());
  }

  @Override
  public Drug createDrugLocally(Drug newDrug) {
    validateDrugDoesNotExist(newDrug);

    em.persist(newDrug);
    return newDrug;
  }

  @Override
  public Drug updateMasterData(int pzn, String name, String description) {
    Drug drug = getDrug(pzn);
    drug.setName(name);
    drug.setDescription(description);
    jmsService.updateMasterDataAtSubsidiaries(drug);
    DrugResource drugResource = createResourceFor(Subsidiary.C_SHARPE);
    drugResource.updateDrug(pzn, createDto(drug));
    return drug;
  }
}
