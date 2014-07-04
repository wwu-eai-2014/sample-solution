package de.java.ejb.statistic;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;

import de.java.ejb.Subsidiary;

@Stateless
public class PrescriptionReportServiceBean implements PrescriptionReportService {

  @Override
  public Collection<PrescriptionReport> getPrescriptionReports(Timespan timespan) {
    ArrayList<PrescriptionReport> result = new ArrayList<>();
    result.add(new PrescriptionReport(Subsidiary.JAVA, 4, 2.5, new Duration(3600)));
    result.add(new PrescriptionReport(Subsidiary.C_SHARPE, 6, 6.5, new Duration(36000)));
    return result;
  }

}
