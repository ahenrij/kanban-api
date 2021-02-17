package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Team;

public class TeamDao extends AbstractJpaDao<Long, Team> {

    public TeamDao() {
        super(Team.class);
    }
}
