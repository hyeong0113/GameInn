package com.cmpt276.gameinn.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

import com.cmpt276.gameinn.models.GroupFinder;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.*;
import com.cmpt276.gameinn.auth.HandleCookie;

@Controller public class GroupFinderController {
    @Autowired private GroupFinderService groupFinderService;
    @Autowired private UserService userService;

	@GetMapping(value = {"/groupfinders", "/groupfinders/{sub}"}) public String groupFinderListPage(@PathVariable(required = false)String sub,
                                                                    Model model, HttpServletRequest request, @AuthenticationPrincipal OidcUser principal) {
        
        String url="";
        if (principal != null) {
            String role = userService.getRoleFromResponse(principal);
            if (!role.equals("admin") && HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME) != sub) {
                model.addAttribute("show", false);
            }
            else {
                model.addAttribute("show", true);
            }
            model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
            url=String.format("/groupfinders/%s/addEdit", HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME));
        }
        else {
            url="/oauth2/authorization/auth0";
        }
        model.addAttribute("url", url);
        model.addAttribute("groupFinders", groupFinderService.getGroupFinders());

        return "groupFinderList";
    }

    @GetMapping("/groupfinders/{sub}/addEdit")
    public String showAddEditGroupFinderPageForCreate(@PathVariable(required = true)String sub, GroupFinder groupFinder, Model model, HttpServletRequest request) {
		model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        return "addEditGroupFinderPage";
    }

    // Assure User is logged in and have an authorization to create
    @PostMapping("/groupfinders/{sub}/addEdit/add") public String addGroupFinder(@PathVariable(required = true)String sub,
                                                                                @Valid GroupFinder groupFinder, BindingResult result,
                                                                                Model model, HttpServletRequest request) {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        if (result.hasErrors()) {
            return "addEditGroupFinderPage";
        }

        User user = userService.getUserBySub(sub);
        GroupFinder temp = groupFinderService.addGroupFinder(groupFinder, user);

		return "redirect:/groupfinders/" + sub;
	}

    @GetMapping(value = {"/groupfinders/detail/{id}", "/groupfinders/{sub}/detail/{id}"}) public String getGroupFinderById(@PathVariable(required = false) String sub,
                                                                                                        @PathVariable Long id, Model model, HttpServletRequest request) {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        model.addAttribute("groupFinder", groupFinderService.getGroupFinderByID(id));
        return "groupFinderDetail";
    }

    @GetMapping("/groupfinders/{sub}/addEdit/{id}")
    public String showAddEditGroupFinderPageForEdit(@PathVariable(required = true)String sub, @PathVariable Long id,
                                                                                GroupFinder groupFinder, Model model, HttpServletRequest request) {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        model.addAttribute("groupFinder", groupFinderService.getGroupFinderByID(id));
        return "addEditGroupFinderPage";
    }

    @RequestMapping("/groupfinders/{sub}/addEdit/edit/{id}") public String editGroupFinder(@PathVariable(required = true)String sub, @PathVariable Long id,
                                                                                @Valid GroupFinder groupFinder, BindingResult result,
                                                                                Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        if (result.hasErrors()) {
            return "addEditGroupFinderPage";
        }

        GroupFinder temp = groupFinderService.updateGroupFinder(id, groupFinder);

		return "redirect:/groupfinders/" + sub;
	}

    @RequestMapping("/groupfinders/{sub}/delete/{id}")
    public String deleteGroupFinder(@PathVariable(required = true)String sub, @PathVariable Long id, Model model, HttpServletRequest request, @AuthenticationPrincipal OidcUser principal) {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        groupFinderService.deleteGroupFinder(id);
        return "redirect:/groupfinders/" + sub;
    }


 }

