package org.kolimaro.springsecuritythymeleaf.service;

import org.kolimaro.springsecuritythymeleaf.dao.RoleDao;
import org.kolimaro.springsecuritythymeleaf.dao.UserDao;
import org.kolimaro.springsecuritythymeleaf.model.Role;
import org.kolimaro.springsecuritythymeleaf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Pavel Tokarev, 28.05.2020
 */

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public List<User> allUsers() {
        return userDao.findAll();
    }

    public boolean saveUser(User user) {
        if (userDao.findUserByUsername(user.getUsername()) != null) {
            return false;
        }
        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(roleDao.findById(1L)));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return true;
    }

    public Role getRoleById(Long id) {
        return roleDao.findById(id);
    }

    public boolean updateUser(User user, Long userId) {
        if (userDao.findById(userId) != null) {
            user.setId(userId);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.update(user);
            return true;
        }
        return false;
    }

    public boolean deleteUser(Long userId) {
        User user = userDao.findById(userId);
        if (user != null) {
            userDao.delete(user);
            return true;
        }
        return false;
    }
}
