package org.kolimaro.springsecuritythymeleaf.dao;

import org.kolimaro.springsecuritythymeleaf.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * @author Pavel Tokarev, 31.05.2020
 */

@Repository
public class RoleDaoImpl implements RoleDao {

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Role findById(Long id) {
        return entityManager.find(Role.class, id);
    }
}
