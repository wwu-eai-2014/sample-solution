package de.java.ejb.statistic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.NotFoundException;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import de.java.domain.Drug;
import de.java.ejb.DrugService;
import de.java.ejb.Subsidiary;
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
    Map<Integer, AggregatedDrugStatistic> statistics = createPznToAggredgatedStatisticMap();
    
    for (Subsidiary subsidiary : Subsidiary.values()) {
      for (DrugStatisticDto statistic : retrieveAllStatisticsFrom(subsidiary)) {
        int pzn = statistic.getPzn();
        if (statistics.containsKey(pzn)) {
          IndividualDrugStatistic individualStatistic = new IndividualDrugStatistic(
              subsidiary, statistic);
          statistics.get(pzn)
              .addIndividualStatistic(individualStatistic);
        }
      }
    }

    // copy into new list because values are not serializable
    return new ArrayList<>(statistics.values());
  }
  
  private Map<Integer, AggregatedDrugStatistic> createPznToAggredgatedStatisticMap() {
    Map<Integer, AggregatedDrugStatistic> pznToAggregatedDrugStatistic = new HashMap<>();
    for (Drug d : drugService.getAllDrugs()) {
      pznToAggregatedDrugStatistic.put(d.getPzn(), new AggregatedDrugStatistic(d));
    }
    return pznToAggregatedDrugStatistic;
  }

  private Collection<DrugStatisticDto> retrieveAllStatisticsFrom(Subsidiary s) {
    ResteasyWebTarget target = new ResteasyClientBuilder().build().target(s.getBaseUri());
    DrugStatisticResource resource = target.proxy(DrugStatisticResource.class);
    Collection<DrugStatisticDto> allStatistics = resource.getAllStatistics();
    return allStatistics;
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
