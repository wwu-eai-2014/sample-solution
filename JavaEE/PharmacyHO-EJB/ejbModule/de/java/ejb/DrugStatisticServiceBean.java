package de.java.ejb;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.NotFoundException;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import de.java.domain.Drug;
import de.java.ejb.statistic.AggregatedDrugStatistic;
import de.java.ejb.statistic.IndividualDrugStatistic;
import de.java.web.drug.stat.DrugStatisticDto;
import de.java.web.drug.stat.DrugStatisticResource;

@Stateless
public class DrugStatisticServiceBean implements DrugStatisticService {

  @EJB
  DrugService drugService;

  @PostConstruct
  public void registerRestEasy() {
    RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
  }

  @Override
  public Collection<AggregatedDrugStatistic> getAllStatistics() {
    // TODO retrieve drug statistic dtos
    // TODO create map pzn->drug
    // TODO combine drug statistic dtos
    return null;
  }

  @Override
  public AggregatedDrugStatistic getStatisticFor(int pzn) {
    Drug drug = drugService.getDrug(pzn);
    AggregatedDrugStatistic result = new AggregatedDrugStatistic(drug);
    for (Subsidiary s : Subsidiary.values()) {
      try {
        result.addIndividualStatistic(new IndividualDrugStatistic(s, retrieveStatistic(pzn, s)));
      } catch (NotFoundException e) {
        // not available at subsidiary
      }
    }
    return result;
  }

  private DrugStatisticDto retrieveStatistic(int pzn, Subsidiary s) {
    ResteasyWebTarget target = new ResteasyClientBuilder().build().target(s.getBaseUri());
    DrugStatisticResource resource = target.proxy(DrugStatisticResource.class);
    return resource.getStatistic(pzn);
  }

}
