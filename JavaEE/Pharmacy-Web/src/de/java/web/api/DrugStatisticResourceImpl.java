package de.java.web.api;

import java.util.Collection;

import javax.ws.rs.Path;

import de.java.web.drug.stat.DrugStatisticDto;
import de.java.web.drug.stat.DrugStatisticResource;

@Path("statistic/drug")
public class DrugStatisticResourceImpl implements DrugStatisticResource {

  @Override
  public Collection<DrugStatisticDto> getAllStatistics() {
    return null;
  }

  @Override
  public DrugStatisticDto getStatistic(int pzn) {
    // TODO ensure existence of drug
    return null;
  }

}
