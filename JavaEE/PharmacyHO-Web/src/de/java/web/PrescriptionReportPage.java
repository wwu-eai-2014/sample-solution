package de.java.web;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.java.ejb.statistic.PrescriptionReport;
import de.java.ejb.statistic.PrescriptionReportService;
import de.java.ejb.statistic.Timespan;

@ManagedBean
@ViewScoped
public class PrescriptionReportPage implements Serializable {

  private static final long serialVersionUID = -8151345257000282538L;
  
  @EJB
  private PrescriptionReportService reportService;
  
  private Collection<PrescriptionReport> reports;
  
  private Timespan timespan = new Timespan();

  public void refresh() {
    reports = null;
  }
  
  public Collection<PrescriptionReport> getReports() {
    if (reports == null) {
      reports = reportService.getPrescriptionReports(getTimespan());
    }
    
    return reports;
  }

  public void setTimespan(Timespan timespan) {
    this.timespan = timespan;
  }

  public Timespan getTimespan() {
    return timespan;
  }

  public long getTotalNumberOfPrescriptions() {
    long result = 0;
    for (PrescriptionReport r : getReports()) {
      result += r.getTotalNumberOfPrescriptions();
    }
    return result;
  }

}
