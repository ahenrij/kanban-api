package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.TeamDao;
import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.domain.Team;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/team")
@Produces({"application/json"})
public class TeamResource {

    TeamDao teamDao = new TeamDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeam(@Parameter(description = "Section to create") Team team) {
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
}
