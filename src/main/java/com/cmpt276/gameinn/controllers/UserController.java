package com.cmpt276.gameinn.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.UserService;
@Controller public class UserController {
	@Autowired private UserService service;

	@GetMapping("/users") public List<User> home() {
		return service.getUsers();
	}
}
