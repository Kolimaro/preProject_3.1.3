package org.kolimaro.springsecuritythymeleaf.dao;

import org.kolimaro.springsecuritythymeleaf.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

/**
 * @author Pavel Tokarev, 28.05.2020
 */

public interface RoleDao {

    Role findById(Long id);
}
