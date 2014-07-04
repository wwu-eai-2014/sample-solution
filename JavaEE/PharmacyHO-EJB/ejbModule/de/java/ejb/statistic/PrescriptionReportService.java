package de.java.ejb.statistic;

import java.util.Collection;

import javax.ejb.Remote;

@Remote
public interface PrescriptionReportService {

  Collection<PrescriptionReport> getPrescriptionReports(Timespan timespan);

}
