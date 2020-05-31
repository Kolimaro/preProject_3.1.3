package org.kolimaro.springsecuritythymeleaf.dao;

import org.kolimaro.springsecuritythymeleaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Pavel Tokarev, 28.05.2020
 */

public interface UserDao {

    User findUserByUsername(String username);

    List<User> findAll();

    void save(User user);

    User findById(Long userId);

    void delete(User user);

    void update(User user);
}
