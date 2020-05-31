package org.kolimaro.springsecuritythymeleaf.dao;

import org.kolimaro.springsecuritythymeleaf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author Pavel Tokarev, 31.05.2020
 */

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public User findUserByUsername(String username) {
        try {
            return (User) entityManager.createQuery("select u from User u where u.username = :username").setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    @Transactional
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public User findById(Long userId) {
        return (User) entityManager.createQuery("select u from User u where u.id = :user_id").setParameter("user_id", userId).getSingleResult();
    }

    @Override
    @Transactional
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        User userFromDb = entityManager.find(User.class, user.getId());
        userFromDb.setPassword(user.getPassword());
        userFromDb.setRoles(user.getRoles());
        userFromDb.setUsername(user.getUsername());
        entityManager.flush();
        entityManager.refresh(userFromDb);
    }

}
