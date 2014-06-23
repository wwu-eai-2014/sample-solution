package de.java.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import de.java.ejb.MergeService;

@ManagedBean
public class NavigationPage implements Serializable {

  private static final long serialVersionUID = 7220517830721425541L;

  @EJB
  private MergeService mergeService;

  public boolean isInNeedOfMerge() {
    return mergeService.isInNeedOfMerge();
  }

  public String merge() {
    mergeService.merge();
    return "/drug/list.xhtml?faces-redirect=true";
  }

}
