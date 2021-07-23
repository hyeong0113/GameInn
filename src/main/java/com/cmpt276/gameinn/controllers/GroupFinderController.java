package com.cmpt276.gameinn.controllers;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cmpt276.gameinn.constant.UserInfo;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.*;
import com.cmpt276.gameinn.wrapper.UserWrapper;


@GetMapping("/") public String home(@AuthenticationPrincipal OidcUser
		principal, HttpServletResponse response, Model model) {
		if (principal != null) {
            String title = principal.getClaims().get("title").toString();
            String gameTitle = getRoleFromResponse(principal);

            GroupFinder groupfinder = service.addGroupFinder(title,gameTitle);
            GroupFinderInfo.setTitle(user.getTitle());

            UserWrapper GroupFinder = new Groupfinder(principal);
            userWrapper.setTitle(groupfinder.getTitle());
            userWrapper.setGameTitle(groupfinder.getGameTitle());

            model.addAttribute("groupfinder", GameFinderInfo.getWrapper());

            return "group_finder page";
        }
        return index;
    }


@Controller public class GroupFinderController {
    @Autowired private GroupFinder service;
    

    @GetMapping("/groupFinders") public List<GroupFinder> groupFinders() {
        return service.getGroupFinders();
    }

    @GetMapping("/Queue/{sub}") public String groupFinder (Model model){
        model.addAttribute("groupFinder", groupFinder.getWrapper());
        return "index";
    }

    @GetMapping("/list") public String groupFinder(Model model) {
		model.addAttribute("user", UserInfo.getWrapper());

		return "list";
	}


    private String getRoleFromResponse(OidcUser principal) {
		String role = principal.getClaims().get(apiRole).toString();
		StringBuilder role_refined = new StringBuilder(role);
		role_refined.deleteCharAt(role.length() - 1);
		role_refined.deleteCharAt(0);
		return role_refined.toString();
	}

 }

