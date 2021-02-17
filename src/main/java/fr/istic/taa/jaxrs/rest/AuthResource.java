package fr.istic.taa.jaxrs.rest;

import com.google.gson.Gson;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.Credentials;
import fr.istic.taa.jaxrs.dto.UserDto;
import fr.istic.taa.jaxrs.utils.Hashing;
import fr.istic.taa.jaxrs.utils.JwtUtil;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Parameter;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Optional;

@Path("/")
@Produces({MediaType.APPLICATION_JSON})
public class AuthResource {

    private final UserDao userDao = new UserDao();

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Parameter(description = "Login credentials", required = true) Credentials credentials) {

        Optional<User> user = userDao.login(credentials);
        if (!user.isPresent()) {
            return Response.status(Response.Status.FORBIDDEN).entity("Bad credentials").build();
        }

        UserDto userDto = UserDto.fromUser(user.get());

        //generate token and add it to JSON result
        String token = JwtUtil.generateToken(userDto);
        JSONObject result = new JSONObject((new Gson()).toJson(userDto));
        result.put("accessToken", token);

        return Response.ok().entity(result.toString()).build();
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@Parameter(description = "User object to register", required = true) User user) {

        //Hash password before saving
        user.setPassword(Hashing.hash(user.getPassword()));

        userDao.save(user);
        return Response.ok().entity(UserDto.fromUser(user)).build();
    }

    @GET
    @Path("token")
    @Secured
    public Response getToken(@Context SecurityContext securityContext) {

        String userId = securityContext.getUserPrincipal().getName();

        User user = userDao.findOne(Long.valueOf(userId));
        String token = JwtUtil.generateToken(UserDto.fromUser(user));

        JSONObject result = new JSONObject();
        result.put("userId", userId);
        result.put("accessToken", token);

        return Response.ok().entity(result.toString()).build();
    }
}
