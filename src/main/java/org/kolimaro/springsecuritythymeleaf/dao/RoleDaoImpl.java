package org.kolimaro.springsecuritythymeleaf.dao;

import org.kolimaro.springsecuritythymeleaf.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Pavel Tokarev, 31.05.2020
 */

@Repository
public class RoleDaoImpl implements RoleDao {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findById(Long id) {
        return entityManager.find(Role.class, id);
    }
}
