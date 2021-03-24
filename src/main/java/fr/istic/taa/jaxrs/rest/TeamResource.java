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
    public Response createTeam(@Context SecurityContext securityContext, @Parameter(description = "Team to create") Team team) {
        //Get the current userId from context
        String userId = securityContext.getUserPrincipal().getName();
        team.setManager(userDao.findOne(Long.valueOf(userId)));
        teamDao.save(team);
        return Response.ok().entity(team).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTeam(@Parameter(description = "Team to update") Team team) {
        try {
            Team teamOld = teamDao.findOne(team.getId());
            teamOld.setId(team.getId());
            teamOld.setTitle(team.getTitle());
            teamDao.update(teamOld);
            return Response.ok().entity(teamOld).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTeam(@Context SecurityContext securityContext, @PathParam("id") Long teamId) {
        try {
            Team team = teamDao.findOne(teamId);
            teamDao.delete(team);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    public Response getTeams(@Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();
        List<Team> teams = teamDao.getTeamsByUserId(Long.parseLong(userId));
        return Response.ok().entity(teams).build();
    }

    @GET
    @Path("/{id}/member")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMembers(@PathParam("id") Long teamId) {
        // return pet
        Team team = teamDao.findOne(teamId);
        List<User> members = team.getMembers();
        if (members == null) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(members).build();
        }
    }

    @POST
    @Path("/{id}/member")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMember(@PathParam("id") Long teamId, User member) {

        Team team = teamDao.findOne(teamId);
        List<User> members = team.getMembers();

        if (members.contains(member)) {
            return Response.noContent().build();
        } else {
            members.add(member);
            team.setMembers(members);
            teamDao.update(team);
            return Response.ok().entity(team).build();
        }
    }

    @GET
    @Path("/{id}/board")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBoards(@PathParam("id") Long teamId) {

        Team team = teamDao.findOne(teamId);
        List<Board> boards = team.getBoards();
        if (boards == null) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(boards).build();
        }
    }
}

