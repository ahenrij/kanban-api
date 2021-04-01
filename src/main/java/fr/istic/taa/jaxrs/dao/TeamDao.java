package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Board;
import fr.istic.taa.jaxrs.domain.Team;
import fr.istic.taa.jaxrs.domain.User;

import java.util.List;

public class TeamDao extends AbstractJpaDao<Long, Team> {

    public TeamDao() {
        super(Team.class);
    }

    public List<Team> getTeamsManagedBy(Long userId) {

        //A selection on User for nothing ?
        return this.entityManager
                .createQuery("SELECT t FROM " + clazz.getName() + " t WHERE t.manager.id = :userId", clazz)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Team> getTeamsSharedWith(Long userId) {
        return this.entityManager
                .createQuery("SELECT t FROM " + clazz.getName() + " t LEFT JOIN t.members m WHERE m.id = :userId", clazz)
                .setParameter("userId", userId)
                .getResultList();
    }

    public boolean deleteTeam(Long teamId) {
        return this.entityManager
                .createQuery("DELETE FROM " + clazz.getName() + " t WHERE t.id = :id")
                .setParameter("id", teamId)
                .executeUpdate() == 1;
    }

    public List<User> getTeamMembers(Long teamId) {

        return this.entityManager
                .createQuery("SELECT m FROM " + clazz.getName() + " t LEFT JOIN t.members m WHERE t.id = :id", User.class)
                .setParameter("id", teamId)
                .getResultList();
    }
}
