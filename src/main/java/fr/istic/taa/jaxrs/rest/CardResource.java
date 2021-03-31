package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.CardDao;
import fr.istic.taa.jaxrs.dao.SectionDao;
import fr.istic.taa.jaxrs.dao.TagDao;
import fr.istic.taa.jaxrs.dao.UserDao;
import fr.istic.taa.jaxrs.domain.Card;
import fr.istic.taa.jaxrs.domain.Tag;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.dto.CardDto;
import fr.istic.taa.jaxrs.dto.mappers.CardMapper;
import fr.istic.taa.jaxrs.dto.mappers.Mappers;
import fr.istic.taa.jaxrs.dto.mappers.UserMapper;
import fr.istic.taa.jaxrs.utils.Secured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/api/card")
@Secured
@Produces({"application/json"})
public class CardResource {

    CardDao cardDao = new CardDao();
    SectionDao sectionDao = new SectionDao();
    TagDao tagDao = new TagDao();
    UserDao userDao = new UserDao();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new Card (in a Section).")
    public Response createCard(@Context SecurityContext securityContext, @Parameter(description = "Card to create") CardDto cardDto) {

        Long sectionId = cardDto.getSectionId();
        Card card = Mappers.INSTANCE.map(cardDto);
        card.setSection(sectionDao.findOne(sectionId));

        cardDao.save(card);
        return Response.ok().entity(card).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a Card (and his Section eventually).")
    public Response updateCard(@Parameter(description = "Card to update") CardDto cardDto) {
        try {
            Card card = cardDao.findOne(cardDto.getId());
            CardMapper.INSTANCE.updateAttrs(cardDto, card);

            if (cardDto.getSectionId() > 0) { //sectionId is defined
                card.setSection(sectionDao.getReference(cardDto.getSectionId()));
            }
            cardDao.update(card);
            return Response.ok().entity(Mappers.INSTANCE.map(cardDto)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a Card.")
    public Response deleteCard(@PathParam("id") Long cardId) {
        try {
            Card card = cardDao.getReference(cardId);
            cardDao.delete(card);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/{id}/tag/{tag_id}")
    @Operation(summary = "Attach a Tag to Card.")
    public Response attachTag(@PathParam("id") Long cardId, @PathParam("tag_id") Long tagId) {

        try {
            Card card = cardDao.findOne(cardId);
            card.getTags().add(tagDao.getReference(tagId));
            cardDao.update(card);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/{id}/assign/{user_id}")
    @Operation(summary = "Attach an Assignee to Card.")
    public Response attachAssignee(@PathParam("id") Long cardId, @PathParam("user_id") Long userId) {

        try {
            Card card = cardDao.findOne(cardId);
            card.getAssignees().add(userDao.getReference(userId));
            cardDao.update(card);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}/tag/{tag_id}")
    @Operation(summary = "Detach a Tag from Card.")
    public Response detachTag(@PathParam("id") Long cardId, @PathParam("tag_id") Long tagId) {

        try {
            Card card = cardDao.findOne(cardId);
            Tag tag = tagDao.findOne(tagId);
            card.getTags().remove(tag);
            cardDao.update(card);
            return Response.ok().entity(tag).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}/assign/{user_id}")
    @Operation(summary = "Detach an Assignee from Card.")
    public Response detachAssignee(@PathParam("id") Long cardId, @PathParam("user_id") Long userId) {

        try {
            Card card = cardDao.findOne(cardId);
            User user = userDao.findOne(userId);
            card.getAssignees().remove(user);
            cardDao.update(card);
            return Response.ok().entity(UserMapper.INSTANCE.map(user)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong: " + e.getMessage()).build();
        }
    }
}
