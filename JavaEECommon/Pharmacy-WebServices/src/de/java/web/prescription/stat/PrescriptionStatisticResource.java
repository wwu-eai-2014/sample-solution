package de.java.web.prescription.stat;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("statistic/prescription/")
public interface PrescriptionStatisticResource {

  @GET
  @Produces(APPLICATION_JSON)
  PrescriptionStatisticDto getStatistic(@QueryParam("start") String start, @QueryParam("end") String end);

}
