package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.TeamDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.domain.Team;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.UserDto;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("/team")
@Secured
@Produces({"application/json"})
public class TeamResource {

    TeamDao teamDao = new TeamDao();
    UserDao userDao = new UserDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeam(@Context SecurityContext securityContext,@Parameter(description = "Section to create") Team team) {
        //Get the current userId from context
        String userId = securityContext.getUserPrincipal().getName();
        team.setManager(userDao.findOne(Long.valueOf(userId)));
        teamDao.save(team);
        return Response.ok().entity(team).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTeam(@Context SecurityContext securityContext, @PathParam("id") Long teamId) {
        try {
            Team team = teamDao.findOne(teamId);

            teamDao.delete(team);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTeam(@Parameter(description = "Team to update") Section section) {
        try {
            Team teamOld = teamDao.findOne(section.getId());
            teamDao.update(teamOld);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
        return Response.ok().entity(section).build();
    }

    @GET
    @Path("/members")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMembers(@PathParam("id") Long teamId) {
        // return pet
        Team team = teamDao.findOne(teamId);
        List<User> members = new ArrayList<>();
        members = team.getMembers();
        if (members == null) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(members).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setMembers(@PathParam("id") Long teamId, List<User> members){
        Team team = teamDao.findOne(teamId);
        if(team.getMembers().contains(members)){
            return Response.noContent().build();
        }else {
            team.setMembers(members);
            return Response.ok().entity(team).build();
        }
    }

    @GET
    @Path("/boards")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBoards(@PathParam("id") Long teamId) {
        // return pet
        Team team = teamDao.findOne(teamId);
        List<Board> boards = new ArrayList<>();
        boards = team.getBoards();
        if (boards == null) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(boards).build();
        }
    }

}

