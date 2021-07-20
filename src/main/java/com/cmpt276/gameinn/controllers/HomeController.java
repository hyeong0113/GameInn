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

import com.cmpt276.gameinn.constant.UserInfo;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.*;
import com.cmpt276.gameinn.wrapper.UserWrapper;

@Controller public class HomeController {
	@Autowired private UserService service;
	private String apiRole = "https://gameinn:us:auth0:com/api/v2/roles";

	// Move to landing page
	@GetMapping("/") public String home(@AuthenticationPrincipal OidcUser
		principal, HttpServletResponse response, Model model) {
		if (principal != null) {
			String sub = principal.getClaims().get("sub").toString();
			String role = getRoleFromResponse(principal);
			
			User user = service.addUser(sub, role);

			UserInfo.setSub(user.getSubId());
			UserWrapper userWrapper = new UserWrapper(principal);
			userWrapper.setSubId(user.getSubId());
			userWrapper.setRole(role);
			userWrapper.setAbout(user.getAbout());
			UserInfo.setWrapper(userWrapper);

			model.addAttribute("user", UserInfo.getWrapper());

			return "landing_page";
		}

		return "index";
	}

	// Move to main page (in our app, it will be clip list page) - June Kwak
	@GetMapping("/main/{sub}") public String main(@PathVariable String sub,
		Model model) {
		model.addAttribute("user", UserInfo.getWrapper());

		return "index";
	}

	// Move to profile page
	@GetMapping("/profile/{sub}") public String profile(@PathVariable String
		sub, Model model) {
		model.addAttribute("user", UserInfo.getWrapper());

		return "index";
	}

	@GetMapping("/list") public String groupFinder(Model model) {
		model.addAttribute("user", UserInfo.getWrapper());

		return "list";
	}

	@GetMapping("/clips") public String addClip(Model model) {
		model.addAttribute("user", UserInfo.getWrapper());

		return "clipList";
	}

	private String getRoleFromResponse(OidcUser principal) {
		String role = principal.getClaims().get(apiRole).toString();
		StringBuilder role_refined = new StringBuilder(role);
		role_refined.deleteCharAt(role.length() - 1);
		role_refined.deleteCharAt(0);
		return role_refined.toString();
	}
}