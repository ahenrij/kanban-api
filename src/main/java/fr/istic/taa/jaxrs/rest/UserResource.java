package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.UserDto;
import fr.istic.taa.jaxrs.dto.mappers.UserMapper;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/user")
@Secured
@Produces({"application/json"})
public class UserResource {

    private final UserDao userDao = new UserDao();

    @GET
    @Operation(summary = "Users list")
    public Response getUsers() {
        return Response.ok().entity(userDao.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get user's information by id")
    public Response getUserById(@PathParam("id") Long userId) {
        // return user
        User user = userDao.findOne(userId);
        return Response.accepted().entity(UserMapper.INSTANCE.map(user)).build();
    }

    @PUT
    @Consumes("application/json")
    @Operation(summary = "Update user information", description = "Update User entity attributes from all UserDto attrs.")
    public Response updateUser(@Parameter(required = true, description = "User to update") UserDto userDto) {

        User user = userDao.findOne(userDto.getId());
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No user found !").build();
        }
        UserMapper.INSTANCE.updateAttrs(userDto, user);
        userDao.update(user);
        return Response.accepted().entity(userDto).build();
    }
}
