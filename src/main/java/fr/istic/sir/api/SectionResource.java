package fr.istic.sir.api;

import fr.istic.sir.dao.BoardDao;
import fr.istic.sir.dao.SectionDao;
import fr.istic.sir.dto.SectionDto;
import fr.istic.sir.dto.mappers.SectionMapper;
import fr.istic.sir.model.Section;
import fr.istic.sir.utils.Secured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/api/section")
@Secured
@Produces({"application/json"})
public class SectionResource {

    SectionDao sectionDao = new SectionDao();
    BoardDao boardDao = new BoardDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new Section on a Board given by his id.")
    public Response createSection(@Context SecurityContext securityContext, @Parameter(description = "Section to create") SectionDto sectionDto) {

        Long boardId = sectionDto.getBoardId();
        Section section = SectionMapper.INSTANCE.map(sectionDto);
        section.setBoard(boardDao.getReference(boardId));

        sectionDao.save(section);
        return Response.ok().entity(sectionDto).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a Section's attributes")
    public Response updateSection(@Parameter(description = "Section to update") SectionDto sectionDto) {
        try {
            Section section = sectionDao.findOne(sectionDto.getId());
            SectionMapper.INSTANCE.updateAttrs(sectionDto, section);

            sectionDao.update(section);
            return Response.ok().entity(sectionDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a Section")
    public Response deleteSection(@PathParam("id") Long sectionId) {
        try {
            Section section = sectionDao.getReference(sectionId);
            sectionDao.delete(section);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }
}
