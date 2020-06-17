package org.kolimaro.springsecuritythymeleaf.service;

import org.kolimaro.springsecuritythymeleaf.dao.RoleDao;
import org.kolimaro.springsecuritythymeleaf.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Pavel Tokarev, 17.06.2020
 */

@Service
public class RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role getRoleById(Long id) {
        return roleDao.findById(id);
    }
}
