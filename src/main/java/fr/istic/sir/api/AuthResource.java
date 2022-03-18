package fr.istic.sir.api;

import com.google.gson.Gson;
import fr.istic.sir.dao.UserDao;
import fr.istic.sir.dto.Credentials;
import fr.istic.sir.dto.UserDto;
import fr.istic.sir.dto.UserRegisterDto;
import fr.istic.sir.dto.mappers.UserMapper;
import fr.istic.sir.model.User;
import fr.istic.sir.utils.Hashing;
import fr.istic.sir.utils.JwtUtil;
import fr.istic.sir.utils.Secured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Optional;

@Path("/api")
@Produces({MediaType.APPLICATION_JSON})
public class AuthResource {

    private final UserDao userDao = new UserDao();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Authenticate user with credentials.", description = "Returns user's information with access token.")
    public Response login(@Parameter(description = "Login Credentials", required = true) Credentials credentials) {

        Optional<User> user = userDao.login(credentials);
        if (!user.isPresent()) {
            return Response.status(Response.Status.FORBIDDEN).entity("Bad credentials").build();
        }

        UserDto userDto = UserMapper.INSTANCE.map(user.get());

        //Generate token and add it to JSON result
        String token = JwtUtil.generateToken(userDto);
        JSONObject result = new JSONObject((new Gson()).toJson(userDto));
        result.put("accessToken", token);

        return Response.ok().entity(result.toString()).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Register a new user.")
    public Response register(@Parameter(description = "User to register", required = true) UserRegisterDto userRegisterDto) {

        User user = new User();
        UserMapper.INSTANCE.updateAttrs(userRegisterDto, user);
        //Hash password before saving
        user.setPassword(Hashing.hash(user.getPassword()));
        userDao.save(user);
        return Response.ok().entity(UserMapper.INSTANCE.map(user)).build();
    }

    @GET
    @Path("/token")
    @Secured
    @Operation(summary = "Refresh token of current authenticated user.", description = "Returns new access token and user's id.")
    public Response refreshToken(@Context SecurityContext securityContext) {

        String userId = securityContext.getUserPrincipal().getName();

        User user = userDao.findOne(Long.valueOf(userId));
        String token = JwtUtil.generateToken(UserMapper.INSTANCE.map(user));

        JSONObject result = new JSONObject();
        result.put("userId", userId);
        result.put("accessToken", token);

        return Response.ok().entity(result.toString()).build();
    }
}
