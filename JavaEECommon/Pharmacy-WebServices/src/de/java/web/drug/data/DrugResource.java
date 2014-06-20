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
import javax.ws.rs.core.Response;

@Path("drug")
public interface DrugResource {

  @GET
  @Produces(APPLICATION_JSON)
  Collection<DrugDto> allDrugs();

  @POST
  @Consumes(APPLICATION_JSON)
  Response createDrug(DrugDto newDrug);

  @PUT
  @Path("{pzn}")
  @Consumes(APPLICATION_JSON)
  Response updateDrug(@PathParam("pzn") int pzn, DrugDto drug);

}
