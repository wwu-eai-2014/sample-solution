package de.java.web.drug.data;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("drug/")
public interface DrugResource {

  @GET
  @Produces(APPLICATION_JSON)
  Collection<DrugDto> allDrugs();

  @GET
  @Path("{pzn}")
  @Produces(APPLICATION_JSON)
  DrugDto getDrug(@PathParam("pzn") int pzn);

  @POST
  @Consumes(APPLICATION_JSON)
  DrugDto createDrug(DrugDto newDrug);

  @PUT
  @Path("{pzn}")
  @Consumes(APPLICATION_JSON)
  DrugDto updateDrug(@PathParam("pzn") int pzn, DrugDto drug);

}
