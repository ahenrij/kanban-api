package fr.istic.taa.jaxrs.rest;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import fr.istic.taa.jaxrs.dao.BoardDao;
import fr.istic.taa.jaxrs.dao.SectionDao;
import fr.istic.taa.jaxrs.dao.TeamDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.domain.Team;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.BoardDto;
import fr.istic.taa.jaxrs.dto.mappers.BoardMapper;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/api/board")
@Secured
@Produces(MediaType.APPLICATION_JSON)
public class BoardResource {

    private final UserDao userDao = new UserDao();
    private final BoardDao boardDao = new BoardDao();
    private final TeamDao teamDao = new TeamDao();
    private final SectionDao sectionDao = new SectionDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new Board and set current user as owner")
    public Response createBoard(@Context SecurityContext securityContext, @Parameter(description = "Board to create for user") Board board) {

        //Get the current userId from context
        String userId = securityContext.getUserPrincipal().getName();

        board.setOwner(userDao.findOne(Long.valueOf(userId)));
        boardDao.save(board);
        return Response.ok().entity(board).build();
    }

    @GET
    @Operation(summary = "Get current user's Boards list")
    public Response getBoards(@Context SecurityContext securityContext) {

        String userId = securityContext.getUserPrincipal().getName();

        List<Board> boards = boardDao.getBoardsByUserId(Long.parseLong(userId));

        return Response.ok().entity(boards).build();
    }


    @GET
    @Path("/{id}")
    @Operation(summary = "Get Board's attributes")
    public Response getBoard(@PathParam("id") Long boardId) {

        try {
            Board board = boardDao.getBoard(boardId);
            return Response.ok(board).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/section")
    @Operation(summary = "Get Board's section", description = "Get board's sections, cards, and tags...")
    public Response getSections(@PathParam("id") Long boardId) {

        try {
            List<Section> sections = sectionDao.getSectionsByBoardId(boardId);
            return Response.ok(sections).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update Board's attributes")
    public Response updateBoard(@Parameter(description = "Board to update") BoardDto boardDto) {

        try {
            Board board = boardDao.findOne(boardDto.getId());
            BoardMapper.INSTANCE.updateAttrs(boardDto, board);
            boardDao.update(board);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
        return Response.ok().entity(boardDto).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a Board")
    public Response deleteBoard(@Context SecurityContext securityContext, @PathParam("id") Long boardId) {
        try {
            String userId = securityContext.getUserPrincipal().getName();
            Board board = boardDao.findOne(boardId);

            if (board.getOwner().getId() != Long.parseLong(userId)) { //if current user is not the owner
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            boardDao.delete(board);
            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
    }

    @POST
    @Path("/{id}/team/{team_id}")
    @Operation(summary = "Share Board with a given Team")
    public Response shareBoardWithTeam(@PathParam("id") Long boardId, @PathParam("team_id") Long teamId) {

        Team team = teamDao.findOne(teamId);
        List<Board> boards = team.getBoards();

        boolean isAlreadyShared = boards.stream().anyMatch(board -> board.getId() == boardId);
        if (isAlreadyShared) {
            return Response.noContent().build();
        }
        boards.add(boardDao.getReference(boardId));
        teamDao.update(team);

        return Response.ok().build();
    }
}
