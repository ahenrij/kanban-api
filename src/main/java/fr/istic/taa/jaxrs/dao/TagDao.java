package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Tag;

public class TagDao extends AbstractJpaDao<Long, Tag> {

    public TagDao() {
        super(Tag.class);
    }
}
