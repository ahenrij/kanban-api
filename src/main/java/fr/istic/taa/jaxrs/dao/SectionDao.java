package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Section;

import java.util.List;

public class SectionDao extends AbstractJpaDao<Long, Section> {

    public SectionDao() {
        super(Section.class);
    }

    public List<Section> getSectionsByBoardId(Long boardId) {

        return this.entityManager
                .createQuery("SELECT s FROM " + clazz.getName() + " s join fetch s.cards c WHERE s.board.id = :boardId", clazz)
                .setParameter("boardId", boardId)
                .getResultList();
    }
}
