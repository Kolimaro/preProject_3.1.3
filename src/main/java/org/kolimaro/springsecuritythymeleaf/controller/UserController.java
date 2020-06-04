package org.kolimaro.springsecuritythymeleaf.controller;


import org.kolimaro.springsecuritythymeleaf.model.User;
import org.kolimaro.springsecuritythymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author Pavel Tokarev, 19.05.2020
 */

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String getUser(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String userList(Model model, User user, Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        model.addAttribute("admin", admin);
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@Valid User user, @RequestParam("role") String role) {
        userService.setRoles(user, role);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid User user,
                             @RequestParam("role") String role,
                             BindingResult result, Model model) {
        user.setId(id);
        userService.setRoles(user, role);
        userService.updateUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {

        userService.deleteUser(id);

        return "redirect:/admin";
    }
}
