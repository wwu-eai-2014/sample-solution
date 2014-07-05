package de.java.ejb.statistic;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import de.java.ejb.Subsidiary;
import de.java.ejb.jms.JmsPrescriptionReportService;
import de.java.web.prescription.stat.PrescriptionStatisticResource;

@Stateless
public class PrescriptionReportServiceBean implements PrescriptionReportService {
  @EJB
  JmsPrescriptionReportService jmsService;

  @PostConstruct
  public void registerRestEasy() {
    RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
  }

  @Override
  public Collection<PrescriptionReport> getPrescriptionReports(Timespan timespan) {
    ArrayList<PrescriptionReport> result = new ArrayList<>();
    result.addAll(jmsService.getPrescriptionReports(timespan));
    result.add(retrieveStatistic(timespan, Subsidiary.C_SHARPE));
    return result;
  }

  private PrescriptionReport retrieveStatistic(Timespan timespan, Subsidiary s) {
    ResteasyWebTarget target = new ResteasyClientBuilder().build().target(s.getBaseUri());
    PrescriptionStatisticResource resource = target.proxy(PrescriptionStatisticResource.class);
    String start = Timespan.format(timespan.getStart());
    String end = Timespan.format(timespan.getEnd());
    return new PrescriptionReport(s, resource.getStatistic(start, end));
  }

}
