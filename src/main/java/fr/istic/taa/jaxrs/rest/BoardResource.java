package fr.istic.taa.jaxrs.rest;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import fr.istic.taa.jaxrs.dao.BoardDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/board")
@Secured
@Produces(MediaType.APPLICATION_JSON)
public class BoardResource {

    private final UserDao userDao = new UserDao();
    private final BoardDao boardDao = new BoardDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBoard(@Context SecurityContext securityContext, @Parameter(description = "Board to create for user") Board board) {

        //Get the current userId from context
        String userId = securityContext.getUserPrincipal().getName();

        board.setOwner(userDao.findOne(Long.valueOf(userId)));
        boardDao.save(board);
        return Response.ok().entity(board).build();
    }

    @GET
    public Response getBoards(@Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();

        List<Board> boards = boardDao.getBoardsByUserId(Long.parseLong(userId));
        return Response.ok().entity(boards).build();
    }


    @GET
    @Path("/{id}")
    public Response getBoard(@PathParam("id") Long boardId) {

        try {
            Board board = boardDao.getBoard(boardId);
            return Response.ok(board).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBoard(@Parameter(description = "Board to update for user") Board board) {

        try {
            Board boardOld = boardDao.findOne(board.getId());
            board.setOwner(boardOld.getOwner());
            boardDao.update(board);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
        return Response.ok().entity(board).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBoard(@Context SecurityContext securityContext, @PathParam("id") Long boardId) {
        try {
            Board board = boardDao.findOne(boardId);

            //TODO : check if user is the real owner

            boardDao.delete(board);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
        return Response.ok().build();
    }
}
