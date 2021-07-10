package com.cmpt276.gameinn.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.*;

@Controller public class HomeController {
	@Autowired private UserService service;
	private String apiRole = "https://gameinn:us:auth0:com/api/v2/roles";

	// Move to landing page
	@GetMapping("/") public String home(@AuthenticationPrincipal OidcUser
		principal, HttpServletResponse response, Model model) {
		if (principal != null) {
			String sub = principal.getClaims().get("sub").toString();
			String name = principal.getClaims().get("name").toString();
			String email = principal.getClaims().get("email").toString();
			String photo = principal.getClaims().get("picture").toString();

			// Get user role
			String role = principal.getClaims().get(apiRole).toString();
			StringBuilder role_refined = new StringBuilder(role);
			role_refined.deleteCharAt(role.length() - 1);
			role_refined.deleteCharAt(0);

			User user = service.addUser(sub, name, email, photo,
				role_refined.toString());
			model.addAttribute("user", user);
			response.addCookie(new Cookie("userID", sub));

			return "landing_page";
		}

		return "index";
	}

	// Move to main page (in our app, it will be clip list page) - June Kwak
	@GetMapping("/main/{sub}") public String main(@PathVariable String sub,
		Model model) {
		User found = service.getUserBySub(sub);
		model.addAttribute("user", found);

		return "index";
	}

	// Move to profile page
	@GetMapping("/profile/{sub}") public String profile(@PathVariable String
		sub, Model model) {
		User found = service.getUserBySub(sub);
		model.addAttribute("user", found);

		return "index";
	}

	@GetMapping("/list") String groupFinder(@CookieValue("userID") String sub,
		Model model) {
		User found = service.getUserBySub(sub);
		model.addAttribute("user", found);

		return "list";
	}

	@GetMapping("/clips") String addClip(@CookieValue("userID") String sub,
		Model model) {
		User found = service.getUserBySub(sub);
		model.addAttribute("user", found);

		return "addClipPage";
	}
}
