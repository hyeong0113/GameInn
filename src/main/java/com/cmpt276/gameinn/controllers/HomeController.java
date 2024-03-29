package com.cmpt276.gameinn.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cmpt276.gameinn.auth.HandleCookie;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.*;

@Controller public class HomeController {
	@Autowired private UserService service;
	@Autowired private IGDBService IGDB;

	// Move to landing page
	@GetMapping("/") public String home(@AuthenticationPrincipal OidcUser
		principal, HttpServletResponse response, HttpServletRequest request,
		Model model) {
		if (principal != null) {
			String sub = principal.getClaims().get("sub").toString();
			String role = service.getRoleFromResponse(principal);
			String name = principal.getClaims().get("name").toString();
			String picture = principal.getClaims().get("picture").toString();
			String email = principal.getClaims().get("email").toString();
			User user = service.addUser(sub, role, name, picture, email);

			HandleCookie.setCookie(request, response, user.getSubId());

			model.addAttribute("user", service.getUserBySub(user.getSubId()));
			return "landing_page";
		}

		return "redirect:/clips";
	}

	// Move to main page (in our app, it will be clip list page) - June Kwak
	@GetMapping("/main/{sub}") public String main(@PathVariable String sub,
		Model model, HttpServletRequest request) {
		model.addAttribute("user", service.getUserBySub(HandleCookie.readCookie(
			request, HandleCookie.COOKIE_NAME)));

		return "index";
	}

	// Move to profile page
	@GetMapping("/profile/{sub}") public String profile(@PathVariable String
		sub, Model model, HttpServletRequest request,
		@AuthenticationPrincipal OidcUser principal) {
		if (principal != null)
			model.addAttribute("user", service.getUserBySub(
				HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));

		model.addAttribute("profile", service.getUserBySub(sub));

		String url = String.format("/profile/%s/edit", sub);
		model.addAttribute("url", url);

		return "profile";
	}

	@GetMapping("/profile/{sub}/edit") public String editProfile(
		@PathVariable String sub, Model model, HttpServletRequest request,
		@AuthenticationPrincipal OidcUser principal) {
		if (principal != null)
			model.addAttribute("user", service.getUserBySub(
				HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
		else return "redirect:/oauth2/authorization/auth0";

		model.addAttribute("profile", service.getUserBySub(sub));

		String url = String.format("/profile/%s/update", sub);
		model.addAttribute("url", url);

		String backurl = String.format("/profile/%s", sub);
		model.addAttribute("backurl", backurl);

		return "profileEdit";
	}

	@RequestMapping("/profile/{sub}/update") public String updateProfile(
		@PathVariable(required = true) String sub, User profile, Model model,
		HttpServletRequest request, @AuthenticationPrincipal OidcUser
		principal) {
		if (principal != null)
			model.addAttribute("user", service.getUserBySub(
				HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));

		model.addAttribute("profile", service.getUserBySub(sub));

		User temp = service.updateUser(sub, profile);

		return "redirect:/profile/" + sub;
	}

	@GetMapping("/apiTest") public String igdbTest(Model model,
		HttpServletRequest request) {
		model.addAttribute("user", service.getUserBySub(HandleCookie.readCookie(
			request, HandleCookie.COOKIE_NAME)));
		return "apiTest";
	}

	@ResponseBody @PostMapping("/getGames") public List<String> getGames(
		@RequestParam("query") String query) {
		List<String> games = new ArrayList<String>();

		try {
			if (!query.isEmpty()) {
				HttpResponse<String> jsonResponse = Unirest.get(
					"https://api.twitch.tv/helix/search/categories")
						.headers(IGDB.getTwitchHeaders())
						.queryString("query", query)
						.queryString("first", 50)
						.asString();
				JSONObject result = new JSONObject(jsonResponse.getBody());
				JSONArray arr = result.getJSONArray("data");

				for (int i = 0; i < arr.length(); i++) {
					JSONObject game = (JSONObject)arr.get(i);
					String name = game.getString("name");
					games.add(name);
				}
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return games;
	}
}
