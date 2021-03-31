package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.TagDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Tag;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Parameter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;


@Path("/api/tag")
@Secured
@Produces({"application/json"})
public class TagResource {

    private final TagDao tagDao = new TagDao();
    private final UserDao userDao = new UserDao();

    @POST
    @Consumes("application/json")
    public Response createTag(@Context SecurityContext securityContext, @Parameter(description = "Tag object to save", required = true) Tag tag) {
        try {
            String userId = securityContext.getUserPrincipal().getName();
            tag.setOwner(userDao.findOne(Long.valueOf(userId)));
            tagDao.save(tag);
            return Response.ok().entity(tag).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @GET
    public Response getTags(@Context SecurityContext securityContext) {
        try {
            String userId = securityContext.getUserPrincipal().getName();
            List<Tag> tags = tagDao.getTagsByUserId(Long.parseLong(userId));
            return Response.ok().entity(tags).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTag(@Parameter(description = "Tag to update") Tag tag) {
        try {
            Tag tagOld = tagDao.findOne(tag.getId());
            tagOld.setName(tag.getName());
            tagOld.setColor(tag.getColor());
            tagDao.update(tagOld);
            return Response.ok().entity(tag).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTag(@Context SecurityContext securityContext, @PathParam("id") Long tagId) {
        try {
            Tag tag = tagDao.getReference(tagId);
            tagDao.delete(tag);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
        return Response.ok().build();
    }
}
