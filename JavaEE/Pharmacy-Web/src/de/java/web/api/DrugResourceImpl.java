package de.java.web.api;

import static javax.ws.rs.core.Response.*;
import static javax.ws.rs.core.Response.Status.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import de.java.domain.Drug;
import de.java.ejb.DrugService;
import de.java.ejb.KeyConstraintViolation;
import de.java.web.drug.data.DrugDto;
import de.java.web.drug.data.DrugResource;

@Path("drug") // to ensure resource is discovered
public class DrugResourceImpl implements DrugResource {

  @EJB
  private DrugService service;

  @Override
  public Collection<DrugDto> allDrugs() {
    Collection<Drug> drugs = service.getAllDrugs();
    Collection<DrugDto> result = new ArrayList<>();
    for (Drug d : drugs) {
      result.add(new DrugDto(d.getPzn(), d.getName(), d.getDescription()));
    }
    return result;
  }

  @Override
  public Response createDrug(DrugDto newDrugDto) {
    Drug newDrug = new Drug();
    newDrug.setPzn(newDrugDto.getPzn());
    newDrug.setName(newDrugDto.getName());
    newDrug.setDescription(newDrugDto.getDescription());
    try {
      service.createDrug(newDrug);
      return ok(newDrugDto).build();
    } catch (KeyConstraintViolation e) {
      return status(CONFLICT).build();
    }
  }

  @Override
  public Response updateDrug(int pzn, DrugDto drugDto) {
    if (pzn != drugDto.getPzn()) {
      return status(BAD_REQUEST).build();
    }
    
    // TODO what if it does not exist?
    service.updateMasterData(drugDto.getPzn(), drugDto.getName(),
        drugDto.getDescription());
    return ok(drugDto).build();
  }

}
