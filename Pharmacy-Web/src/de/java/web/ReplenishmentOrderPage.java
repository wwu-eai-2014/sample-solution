package de.java.web;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;

import de.java.domain.ReplenishmentOrder;
import de.java.ejb.ReplenishmentOrderService;
import de.java.web.util.Util;

@ManagedBean
public class ReplenishmentOrderPage {

  @EJB
  private ReplenishmentOrderService orderService;

  private long id;

  private ReplenishmentOrder order;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void ensureInitialized(){
    try{
      if(getOrder() != null)
        // Success
        return;
    } catch(EJBException e) {
      e.printStackTrace();
    }
    Util.redirectToRoot();
  }

  private ReplenishmentOrder getOrder() {
    if (order == null) {
      order = orderService.getOrder(id);
    }
    return order;
  }

  public void setOrder(ReplenishmentOrder order) {
    this.order = order;
  }
}
