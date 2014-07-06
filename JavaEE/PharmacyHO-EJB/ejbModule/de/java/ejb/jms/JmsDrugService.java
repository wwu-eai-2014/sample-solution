package de.java.ejb.jms;

import javax.ejb.Remote;

import de.java.domain.Drug;

@Remote
public interface JmsDrugService {

  void createAtSubsidiaries(Drug drug);

  void updateMasterDataAtSubsidiaries(Drug drug);

}
