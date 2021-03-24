package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.TagDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.domain.Tag;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


@Path("/tag")
@Secured
@Produces({"application/json"})
public class TagResource {

    private final TagDao tagDao = new TagDao();

    @POST
    @Consumes("application/json")
    public Response addTag(@Parameter(description = "Tag object to save", required = true) Tag tag) {
        // add tag
        TagDao tagDao = new TagDao();
        tagDao.save(tag);
        return Response.ok().entity("SUCCESS").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTag(@Parameter(description = "Tag to update") Tag tag) {
         try {
            Tag tagOld = tagDao.findOne(tag.getId());
            tag.setCards(tagOld.getCards());
            tagDao.update(tag);
        } catch (Exception e) {
        return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
    }
        return Response.ok().entity(tag).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTag(@Context SecurityContext securityContext, @PathParam("id") Long tagId) {
        try {
            Tag tag = tagDao.findOne(tagId);
            tagDao.delete(tag);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
        return Response.ok().build();
    }
}
