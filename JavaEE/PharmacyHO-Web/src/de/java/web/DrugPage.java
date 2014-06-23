package de.java.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.java.domain.Drug;
import de.java.ejb.DrugService;
import de.java.ejb.statistic.AggregatedDrugStatistic;
import de.java.ejb.statistic.DrugStatisticService;
import de.java.web.util.Util;

@ManagedBean
@ViewScoped
public class DrugPage implements Serializable {
  private static final long serialVersionUID = -7325343421585931495L;

  @EJB
  private DrugService drugService;

  @EJB
  private DrugStatisticService statisticService;

  private int pzn;
  private Drug drug;

  private AggregatedDrugStatistic drugStatistic;

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
    return toDrugPage();
  }

  private String toDrugPage() {
    return "/drug/details.xhtml?faces-redirect=true&pzn=" + pzn;
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

  public AggregatedDrugStatistic getAggregatedDrugStatistic() {
    if (drugStatistic == null) {
      drugStatistic = statisticService.getStatisticFor(getPzn());
    }
    return drugStatistic;
  }
}
