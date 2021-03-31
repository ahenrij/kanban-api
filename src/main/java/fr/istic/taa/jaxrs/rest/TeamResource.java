package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.TeamDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.domain.Team;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.TeamDto;
import fr.istic.taa.jaxrs.dto.mappers.TeamMapper;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("/api/team")
@Secured
@Produces({"application/json"})
public class TeamResource {

    private final TeamDao teamDao = new TeamDao();
    private final UserDao userDao = new UserDao();

    // TEAM ENTITY

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new team entity", description = "Create new Team entity with current authorized user as owner.")
    public Response createTeam(@Context SecurityContext securityContext, @Parameter(description = "Team to create") Team team) {
        //Get the current userId from context
        String userId = securityContext.getUserPrincipal().getName();
        team.setManager(userDao.findOne(Long.valueOf(userId)));
        teamDao.save(team);
        return Response.ok().entity(team).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a Team.")
    public Response updateTeam(@Parameter(description = "Team to update") TeamDto teamDto) {
        try {
            Team team = teamDao.findOne(teamDto.getId());
            TeamMapper.INSTANCE.updateAttrs(teamDto, team);
            teamDao.update(team);
            return Response.ok().entity(team).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a Team", description = "Returns Forbidden status if current user is not the owner.")
    public Response deleteTeam(@Context SecurityContext securityContext, @PathParam("id") Long teamId) {
        try {
            long userId = Long.parseLong(securityContext.getUserPrincipal().getName());
            Team team = teamDao.findOne(teamId);
            if (team.getManager().getId() != userId) { // only the manager of a team can delete it !
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            teamDao.delete(team);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @GET
    @Operation(summary = "List of teams in which user is a member")
    public Response getTeams(@Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();
        List<Team> teams = teamDao.getTeamsSharedWith(Long.parseLong(userId));
        return Response.ok().entity(teams).build();
    }

    @GET
    @Path("/managed")
    @Operation(summary = "List of teams in which user is the manager")
    public Response getManagedTeams(@Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();
        List<Team> teams = teamDao.getTeamsManagedBy(Long.parseLong(userId));
        return Response.ok().entity(teams).build();
    }


    // TEAM MEMBER

    @GET
    @Path("/{id}/member")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "List of team members")
    public Response getMembers(@Context SecurityContext securityContext, @PathParam("id") Long teamId) {

        String userId = securityContext.getUserPrincipal().getName();

        Team team = teamDao.findOne(teamId);
        List<User> members = new ArrayList<>(team.getMembers());
        members.add(team.getManager()); //add the manager

        //check if user is member of the team
        boolean isMember = members.stream().anyMatch(user -> user.getId() == Long.parseLong(userId));
        if (!isMember) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok().entity(members).build();
    }

    @POST
    @Path("/{id}/member/{member_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a member to the Team")
    public Response addMember(@PathParam("id") Long teamId, @PathParam("member_id") Long memberId) {

        //get user by id or email ?
        Team team = teamDao.findOne(teamId);
        List<User> members = team.getMembers();

        boolean isAlreadyMember = members.stream().anyMatch(user -> user.getId() == memberId);
        if (isAlreadyMember) {
            return Response.noContent().build();
        }

        members.add(userDao.getReference(memberId));
        teamDao.update(team);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}/member/{member_id}")
    @Operation(summary = "Remove a member from the Team")
    public Response removeMember(@PathParam("id") Long teamId, @PathParam("member_id") Long memberId) {

        try {
            Team team = teamDao.findOne(teamId);
            team.getMembers().remove(userDao.getReference(memberId));
            teamDao.update(team);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/board")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBoards(@PathParam("id") Long teamId) {

        try {
            List<Board> boards = teamDao.getTeamBoards(teamId);
            return Response.ok().entity(boards).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }
}

