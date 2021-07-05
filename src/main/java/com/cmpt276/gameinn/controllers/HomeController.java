package com.cmpt276.gameinn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmpt276.gameinn.services.*;

@Controller
public class HomeController {
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            service.addUser(principal.getClaims().get("sub").toString());
            model.addAttribute("profile", principal.getClaims());
        }
        return "index";
    }
}
