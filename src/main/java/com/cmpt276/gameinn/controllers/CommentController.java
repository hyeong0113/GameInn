package com.cmpt276.gameinn.controllers;

import com.cmpt276.gameinn.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmpt276.gameinn.auth.HandleCookie;
import com.cmpt276.gameinn.models.Comment;
import com.cmpt276.gameinn.models.GroupFinder;
import com.cmpt276.gameinn.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller public class CommentController {
	@Autowired private CommentService commentService;
	@Autowired private UserService userService;
	@Autowired private GroupFinderService groupFinderService;

	// Assure User is logged in and have an authorization to create
	@PostMapping("/comments/{sub}/{groupfinderId}/add") public
	String addComment(@PathVariable(required = true) String sub,
		@PathVariable("groupfinderId") Long groupfinderId, @Valid Comment
		commentCreate, BindingResult result, Model model, HttpServletRequest
		request) {
		model.addAttribute("user", userService.getUserBySub(
			HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));

		if (result.hasErrors())
			return "addEditGroupFinderPage";

		User user = userService.getUserBySub(sub);
		GroupFinder groupFinder = groupFinderService.getGroupFinderByID(
			groupfinderId);
		Comment temp = commentService.addComment(commentCreate, user,
			groupFinder);

		return "redirect:/groupfinders/" + sub + "/detail/" +
			   groupFinder.getId();
	}

	@RequestMapping("/comments/{sub}/edit/{id}") public String editComment(
		@PathVariable(required = true) String sub, @PathVariable Long id,
		@Valid Comment comment, BindingResult result, Model model,
		HttpServletRequest request)
	throws Exception {
		model.addAttribute("user", userService.getUserBySub(
			HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));

		if (result.hasErrors())
			return "addEditGroupFinderPage";

		Comment temp = commentService.updateComment(id, comment);

		return "redirect:/groupfinders/" + sub + "/detail/" +
			   temp.getGroupFinder().getId();
	}

	@RequestMapping("/comments/{sub}/{groupfinderId}/delete/{id}") public
	String deleteComment(@PathVariable(required = true) String sub,
		@PathVariable("groupfinderId") Long groupfinderId,
		@PathVariable("id") Long id, Model model, HttpServletRequest request,
		@AuthenticationPrincipal OidcUser principal) {
		model.addAttribute("user", userService.getUserBySub(
			HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
		commentService.deleteComment(id);
		return "redirect:/groupfinders/" + sub + "/detail/" + groupfinderId;
	}
}
