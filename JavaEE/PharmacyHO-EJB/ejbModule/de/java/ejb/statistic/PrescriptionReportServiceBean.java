package de.java.ejb.statistic;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.java.ejb.Subsidiary;
import de.java.ejb.jms.JmsPrescriptionReportService;

@Stateless
public class PrescriptionReportServiceBean implements PrescriptionReportService {
  @EJB
  JmsPrescriptionReportService jmsService;

  @Override
  public Collection<PrescriptionReport> getPrescriptionReports(Timespan timespan) {
    ArrayList<PrescriptionReport> result = new ArrayList<>();
    result.addAll(jmsService.getPrescriptionReports(timespan));
    result.add(new PrescriptionReport(Subsidiary.C_SHARPE, 6, 6.5, new Duration(36000)));
    return result;
  }

}
