package com.cmpt276.gameinn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.*;

@Controller
public class HomeController {
    @Autowired
    private UserService service;

    // Move to main page (in our app, it will be clip list page)
    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser principal, Model model) {
        if (principal != null) {
            String sub = principal.getClaims().get("sub").toString();
            String name = principal.getClaims().get("name").toString();
            String email = principal.getClaims().get("email").toString();
            String photo = principal.getClaims().get("picture").toString();

            User profile = service.addUser(sub, name, email, photo);
            model.addAttribute("profile", profile);
        }
        return "index";
    }

    // Move to profile page
    @GetMapping("/profile/{sub}")
    public String profile(@PathVariable String sub, Model model) {
        User found = service.getUserBySub(sub);
        model.addAttribute("userInfo", found);

        return "profile";
    }
}
