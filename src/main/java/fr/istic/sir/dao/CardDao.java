package fr.istic.sir.dao;

import fr.istic.sir.dao.generic.AbstractJpaDao;
import fr.istic.sir.model.Card;

public class CardDao extends AbstractJpaDao<Long, Card> {

    public CardDao() {
        super(Card.class);
    }
}
