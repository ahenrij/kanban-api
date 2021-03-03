package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.domain.Team;

import java.util.List;

public class TeamDao extends AbstractJpaDao<Long, Team> {

    public TeamDao() {
        super(Team.class);
    }

    public List<Team> getTeamsByUserId(Long userId) {

        return this.entityManager
                .createQuery("SELECT b FROM " + clazz.getName() + " b WHERE b.manager.id = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }
}
