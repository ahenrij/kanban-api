package fr.istic.taa.jaxrs.dao;


import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Board;

import java.util.List;

public class BoardDao extends AbstractJpaDao<Long, Board> {

    public BoardDao() {
        super(Board.class);
    }

    public List<Board> getBoardsByUserId(Long userId) {

        return this.entityManager
                .createQuery("SELECT b FROM " + clazz.getName() + " b WHERE b.owner.id = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }
}
