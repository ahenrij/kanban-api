package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.CardDao;
import fr.istic.taa.jaxrs.dao.SectionDao;
import fr.istic.taa.jaxrs.dao.TagDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/section")
@Produces({"application/json"})
public class SectionResource {

    SectionDao sectionDao = new SectionDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSection(@Context SecurityContext securityContext, @Parameter(description = "Section to create") Section section) {
        sectionDao.save(section);
        return Response.ok().entity(section).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSection(@Context SecurityContext securityContext, @PathParam("id") Long sectionId) {
        try {
            Section section = sectionDao.findOne(sectionId);

            sectionDao.delete(section);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSection(@Parameter(description = "Section to update") Section section) {
        try {
            Section sectionOld = sectionDao.findOne(section.getId());
            sectionDao.update(sectionOld);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
        return Response.ok().entity(section).build();
    }
}
