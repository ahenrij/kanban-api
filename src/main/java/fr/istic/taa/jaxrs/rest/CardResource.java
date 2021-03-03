package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.CardDao;
import fr.istic.taa.jaxrs.dao.TagDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.domain.Tag;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/card")
@Secured
@Produces({"application/json"})
public class CardResource {
    UserDao userDao = new UserDao();
    CardDao cardDao = new CardDao();
    TagDao tagao = new TagDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBoard(@Context SecurityContext securityContext, @Parameter(description = "Card to create") Card card) {
        cardDao.save(card);
        return Response.ok().entity(card).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCard(@Context SecurityContext securityContext, @PathParam("id") Long cardId) {
        try {
            Card card = cardDao.findOne(cardId);

            //TODO : check if user is the real owner

            cardDao.delete(card);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCard(@Parameter(description = "Tag to update") Card card) {
        try {
            Card cardOld = cardDao.findOne(card.getId());
            cardDao.update(cardOld);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
        }
        return Response.ok().entity(card).build();
    }
}
