package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Card;

public class CardDao extends AbstractJpaDao<Long, Card> {

    public CardDao() {
        super(Card.class);
    }
}
