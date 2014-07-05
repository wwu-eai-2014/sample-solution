package de.java.ejb.jms;
import java.util.Collection;

import javax.ejb.Remote;

import de.java.ejb.statistic.PrescriptionReport;
import de.java.ejb.statistic.Timespan;

@Remote
public interface JmsPrescriptionReportService {
	/**
	 * @param timespan Limits the timespan
	 * @return Collection of all reports that were sent by the subsidiaries.
	 */
	Collection<PrescriptionReport> getPrescriptionReports(Timespan timespan);
}
