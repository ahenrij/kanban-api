package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.CardDao;
import fr.istic.taa.jaxrs.dao.SectionDao;
import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.domain.Section;
import fr.istic.taa.jaxrs.dto.CardDto;
import fr.istic.taa.jaxrs.dto.mappers.Mappers;
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

    CardDao cardDao = new CardDao();
    SectionDao sectionDao = new SectionDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCard(@Context SecurityContext securityContext, @Parameter(description = "Card to create") CardDto cardDto) {

        Long sectionId = cardDto.getSectionId();
        Card card = Mappers.INSTANCE.map(cardDto);
        card.setSection(sectionDao.findOne(sectionId));

        cardDao.save(card);
        return Response.ok().entity(card).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCard(@Parameter(description = "Card to update") CardDto cardDto) {
        try {
            Card newCard = Mappers.INSTANCE.map(cardDto);
            if (cardDto.getSectionId() > 0) { // is defined
                newCard.setSection(sectionDao.findOne(cardDto.getSectionId()));
            }
            cardDao.update(newCard);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
        return Response.ok().entity(Mappers.INSTANCE.map(cardDto)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCard(@PathParam("id") Long cardId) {
        try {
            Card card = cardDao.findOne(cardId);
            cardDao.delete(card);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
        return Response.ok().build();
    }
}
