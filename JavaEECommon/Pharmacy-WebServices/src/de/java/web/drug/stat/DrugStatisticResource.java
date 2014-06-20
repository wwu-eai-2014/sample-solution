package de.java.web.drug.stat;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("statistic/drug")
public interface DrugStatisticResource {

  @GET
  @Produces(APPLICATION_JSON)
  Collection<DrugStatisticDto> getAllStatistics();

  @GET
  @Path("{pzn}")
  @Produces(APPLICATION_JSON)
  DrugStatisticDto getStatistic(@PathParam("pzn") int pzn);

}
