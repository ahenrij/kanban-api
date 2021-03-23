package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.BoardDao;
import fr.istic.taa.jaxrs.dao.SectionDao;
import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.dto.SectionDto;
import fr.istic.taa.jaxrs.dto.mappers.Mappers;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/section")
@Secured
@Produces({"application/json"})
public class SectionResource {

    SectionDao sectionDao = new SectionDao();
    BoardDao boardDao = new BoardDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSection(@Context SecurityContext securityContext, @Parameter(description = "Section to create") SectionDto sectionDto) {

        Long boardId = sectionDto.getBoardId();
        Section section = Mappers.INSTANCE.map(sectionDto);
        section.setBoard(boardDao.findOne(boardId));

        sectionDao.save(section);
        return Response.ok().entity(section).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSection(@Parameter(description = "Section to update") Section section) {
        try {
            Section sectionOld = sectionDao.findOne(section.getId());
            section.setBoard(sectionOld.getBoard());
            sectionDao.update(section);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
        return Response.ok().entity(section).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSection(@PathParam("id") Long sectionId) {
        try {
            Section section = sectionDao.findOne(sectionId);
            sectionDao.delete(section);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
        return Response.ok().build();
    }
}
