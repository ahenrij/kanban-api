package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.UserDto;
import fr.istic.taa.jaxrs.dto.mappers.Mappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces({"application/json"})
public class UserResource {

    private final UserDao userDao = new UserDao();

    @GET
    @Path("/{id}")
    @Operation(summary = "Get user informations by id")
    public Response getUserById(@PathParam("id") Long userId) {
        // return pet
        User user = userDao.findOne(userId);
        if (user == null) {
            return Response.noContent().build();
        } else {
            return Response.accepted().entity(Mappers.INSTANCE.map(user)).build();
        }
    }

    @PUT
    @Consumes("application/json")
    public Response updateUser(@Parameter(required = true, description = "User to update") User user) {

        User userToUpdate = userDao.findOne(user.getId());
        if (userToUpdate == null) {
            return Response.noContent().build();
        }

        userToUpdate = userDao.update(user);
        return Response.accepted().entity(Mappers.INSTANCE.map(userToUpdate)).build();
    }

    @POST
    @Consumes("application/json")
    public Response addUser(@Parameter(description = "User object to save", required = true) User user) {
        // add user
        UserDao userDao = new UserDao();
        userDao.save(user);
        return Response.ok().entity("SUCCESS").build();
    }
}
