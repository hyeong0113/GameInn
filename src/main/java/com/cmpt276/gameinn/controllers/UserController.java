package com.cmpt276.gameinn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import com.cmpt276.gameinn.services.UserService;
import com.cmpt276.gameinn.models.User;
@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public List<User> home() {
        return service.getUsers();
    }    
}
