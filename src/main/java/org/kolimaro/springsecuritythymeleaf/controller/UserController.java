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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String user(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication, Model model) {
        User admin = (User) authentication.getPrincipal();
        model.addAttribute("admin", admin);
        return "admin";
    }
}
