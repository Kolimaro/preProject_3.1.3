package org.kolimaro.springsecuritythymeleaf.controller;

import org.kolimaro.springsecuritythymeleaf.model.Role;
import org.kolimaro.springsecuritythymeleaf.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Tokarev, 17.06.2020
 */

@RestController
@RequestMapping("/api")
public class RoleRestController {

    private final RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getRole(@RequestParam(name = "jsonRoles") String jsonRoles) {
        final List<Role> roles = new ArrayList<>();
        if (jsonRoles.contains("user")) {
            roles.add(roleService.getRoleById(1L));
        }
        if (jsonRoles.contains("admin")) {
            roles.add(roleService.getRoleById(2L));
        }
        return !roles.isEmpty()
                ? new ResponseEntity<>(roles, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
