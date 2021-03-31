package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.TagDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Tag;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.TagDto;
import fr.istic.taa.jaxrs.dto.mappers.TagMapper;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Create a Tag.")
    public Response createTag(@Context SecurityContext securityContext, @Parameter(description = "Tag object to save", required = true) Tag tag) {
        try {
            String userId = securityContext.getUserPrincipal().getName();
            tag.setOwner(userDao.getReference(Long.valueOf(userId)));
            tagDao.save(tag);
            return Response.ok().entity(tag).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @GET
    @Operation(summary = "Get Tags create by current user.")
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
    @Operation(summary = "Update Tag's attributes.")
    public Response updateTag(@Parameter(description = "Tag to update") TagDto tagDto) {
        try {
            Tag tag = tagDao.findOne(tagDto.getId());
            TagMapper.INSTANCE.updateAttrs(tagDto, tag);
            tagDao.update(tag);
            return Response.ok().entity(tag).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a Tag.")
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
