package fr.istic.sir.dao;

import fr.istic.sir.dao.generic.AbstractJpaDao;
import fr.istic.sir.dto.Credentials;
import fr.istic.sir.model.User;
import fr.istic.sir.utils.Hashing;

import java.util.Optional;

public class UserDao extends AbstractJpaDao<Long, User> {

    public UserDao() {
        super((User.class));
    }

    public Optional<User> login(Credentials credentials) {

        String query = "select u from " + clazz.getName() + " as u where u.email = ?1 and u.password = ?2";

        try {
            return Optional.of(entityManager.createQuery(query, clazz)
                    .setParameter(1, credentials.getEmail())
                    .setParameter(2, Hashing.hash(credentials.getPassword()))
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
