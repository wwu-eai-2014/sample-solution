package de.java.web;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.java.domain.Drug;
import de.java.ejb.DrugService;
import de.java.web.util.Util;

@ManagedBean
@ViewScoped
public class DrugPage {

  @EJB
  private DrugService drugService;

  private int pzn;

  private Drug drug;

  public int getPzn() {
    return pzn;
  }

  public void setPzn(int pzn) {
    this.pzn = pzn;
    init();
  }

  private void init() {
    drug = null;
  }

  public void ensureInitialized(){
    try{
      if(getDrug() != null)
        // Success
        return;
    } catch(EJBException e) {
      e.printStackTrace();
    }
    Util.redirectToRoot();
  }

  public String submitMasterDataChanges() {
    drug = drugService.updateMasterData(drug.getPzn(), drug.getName(), drug.getDescription());
    return returnToDrugPage();
  }

  private String returnToDrugPage() {
    return "/drug/details.xhtml?faces-redirect=true&pzn=" + pzn;
  }

  public String submitReplenishmentConfigChanges() {
    drug = drugService.updateReplenishmentConfig(drug.getPzn(), drug.getMinimumInventoryLevel(), drug.getOptimalInventoryLevel());
    return returnToDrugPage();
  }

  public Drug getDrug() {
    if (drug == null) {
      drug = drugService.getDrug(pzn);
    }
    return drug;
  }

  public void setDrug(Drug drug) {
    this.drug = drug;
  }
}
