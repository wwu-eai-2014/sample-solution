package de.java.web.api;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;

import de.java.domain.Drug;
import de.java.ejb.DrugService;
import de.java.ejb.PrescriptionService;
import de.java.ejb.ReplenishmentOrderService;
import de.java.web.drug.stat.DrugStatisticDto;
import de.java.web.drug.stat.DrugStatisticResource;

@Path("statistic/drug")
public class DrugStatisticResourceImpl implements DrugStatisticResource {

  @EJB
  PrescriptionService prescriptionService;

  @EJB
  ReplenishmentOrderService orderService;

  @EJB
  DrugService drugService;

  @Override
  public Collection<DrugStatisticDto> getAllStatistics() {
    Collection<DrugStatisticDto> result = new ArrayList<>();
    for (Drug drug : drugService.getAllDrugs()) {
      result.add(retrieveAndCreateStatisticFor(drug));
    }
    return result;
  }
  
  private DrugStatisticDto retrieveAndCreateStatisticFor(Drug drug) {
    long pendingPositions = orderService.getQuantityPendingForDrug(drug.getPzn());
    long unfulfilledItems = prescriptionService.getQuantityUnfulfilledForDrug(drug.getPzn());
    return createDrugStatisticDto(drug, pendingPositions, unfulfilledItems);
  }
  
  private DrugStatisticDto createDrugStatisticDto(Drug drug,
      long pendingPositions, long unfulfilledItems) {
    return new DrugStatisticDto(drug.getPzn(), drug.getStock(), drug.getMinimumInventoryLevel(), drug.getOptimalInventoryLevel(), pendingPositions, unfulfilledItems);
  }
  
  @Override
  public DrugStatisticDto getStatistic(int pzn) {
    Drug drug = drugService.getDrug(pzn);
    if (drug == null) {
      throw new NotFoundException();
    }
    return retrieveAndCreateStatisticFor(drug);
  }

}
