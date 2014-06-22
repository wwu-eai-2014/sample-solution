package de.java.web.api;


import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;

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
  public DrugDto getDrug(int pzn) {
    validateDrugExists(pzn);
    Drug d = service.getDrug(pzn);
    if (d == null) {
      throw new NotFoundException();
    }
    return new DrugDto(d.getPzn(), d.getName(), d.getDescription());
  }

  private void validateDrugExists(int pzn) {
    Drug drug = service.getDrug(pzn);
    if (drug == null) {
      throw new NotFoundException();
    }
  };

  @Override
  public DrugDto createDrug(DrugDto newDrugDto) {
    Drug newDrug = new Drug();
    newDrug.setPzn(newDrugDto.getPzn());
    newDrug.setName(newDrugDto.getName());
    newDrug.setDescription(newDrugDto.getDescription());
    try {
      service.createDrug(newDrug);
      return newDrugDto;
    } catch (KeyConstraintViolation e) {
      throw new BadRequestException();
    }
  }

  @Override
  public DrugDto updateDrug(int pzn, DrugDto drugDto) {
    validatePznCorrespondence(pzn, drugDto);
    validateDrugExists(pzn);
    
    service.updateMasterData(drugDto.getPzn(), drugDto.getName(),
        drugDto.getDescription());
    return drugDto;
  }

  private void validatePznCorrespondence(int pzn, DrugDto drugDto) {
    if (pzn != drugDto.getPzn()) {
      throw new BadRequestException();
    }
  }

}
