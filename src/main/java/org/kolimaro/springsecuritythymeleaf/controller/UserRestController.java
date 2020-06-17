package org.kolimaro.springsecuritythymeleaf.controller;

import org.kolimaro.springsecuritythymeleaf.model.User;
import org.kolimaro.springsecuritythymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Pavel Tokarev, 08.06.2020
 */

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAll() {
        final List<User> users = userService.allUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user != null
                ? new ResponseEntity<>(new ArrayList<>(Collections.singletonList(user)), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody User user) {
        final boolean updated = userService.updateUser(user, id);
        return updated
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = userService.deleteUser(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
