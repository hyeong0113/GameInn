package com.cmpt276.gameinn.controllers;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

import com.cmpt276.gameinn.constant.UserInfo;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.*;
import com.cmpt276.gameinn.wrapper.UserWrapper;

@Controller public class HomeController {
	@Autowired private UserService service;
	@Autowired private IGDBService IGDB;

	private String apiRole = "https://gameinn:us:auth0:com/api/v2/roles";

	private String getRoleFromResponse(OidcUser principal) {
		String role = principal.getClaims().get(apiRole).toString();
		StringBuilder role_refined = new StringBuilder(role);
		role_refined.deleteCharAt(role.length() - 1);
		role_refined.deleteCharAt(0);
		return role_refined.toString();
	}

	// Move to landing page
	@GetMapping("/") public String home(@AuthenticationPrincipal OidcUser
		principal, HttpServletResponse response, Model model) {
		if (principal != null) {
			String sub = principal.getClaims().get("sub").toString();
			String role = getRoleFromResponse(principal);
			String name = principal.getClaims().get("name").toString();
			String picture = principal.getClaims().get("picture").toString();
			User user = service.addUser(sub, role, name, picture);

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

		return "profile";
	}

	@GetMapping("/apiTest") public String igdbTest(Model model) {
		model.addAttribute("user", UserInfo.getWrapper());
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
