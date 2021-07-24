package com.cmpt276.gameinn.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmpt276.gameinn.models.GroupFinder;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.*;
import com.cmpt276.gameinn.constant.UserInfo;

@Controller public class GroupFinderController {
    @Autowired private GroupFinderService groupFinderService;
    @Autowired private UserService userService;

	@GetMapping(value = {"/groupfinders", "/groupfinders/{sub}"}) public String groupFinderListPage(@PathVariable(required = false)String sub, Model model) {
        model.addAttribute("user", UserInfo.getWrapper());
        model.addAttribute("groupFinders", groupFinderService.getGroupFinders());

        return "groupFinderList";
    }

    @GetMapping("/groupfinders/{sub}/addEdit")
    public String showAddEditGroupFinderPageForCreate(@PathVariable(required = true)String sub, GroupFinder groupFinder, Model model) {
        model.addAttribute("user", UserInfo.getWrapper());
        return "addEditGroupFinderPage";
    }

    // Assure User is logged in and have an authorization to create
    @PostMapping("/groupfinders/{sub}/addEdit/add") public String addGroupFinder(@PathVariable(required = true)String sub,
                                                                                @Valid GroupFinder groupFinder, BindingResult result, Model model) {
        model.addAttribute("user", UserInfo.getWrapper());
        if (result.hasErrors()) {
            return "addEditGroupFinderPage";
        }

        User user = userService.getUserBySub(sub);
        GroupFinder temp = groupFinderService.addGroupFinder(groupFinder, user);

		return "redirect:/groupfinders/" + sub;
	}

    @GetMapping(value = {"/groupfinders/detail/{id}", "/groupfinders/{sub}/detail/{id}"}) public String getGroupFinderById(@PathVariable(required = false) String sub, @PathVariable Long id, Model model) {
        model.addAttribute("groupFinder", groupFinderService.getGroupFinderByID(id));
        return "groupFinderDetail";
    }

    @GetMapping("/groupfinders/{sub}/addEdit/{id}")
    public String showAddEditGroupFinderPageForEdit(@PathVariable(required = true)String sub, @PathVariable Long id, GroupFinder groupFinder, Model model) {
        model.addAttribute("user", UserInfo.getWrapper());
        model.addAttribute("groupFinder", groupFinderService.getGroupFinderByID(id));
        return "addEditGroupFinderPage";
    }

    @RequestMapping("/groupfinders/{sub}/addEdit/edit/{id}") public String editGroupFinder(@PathVariable(required = true)String sub, @PathVariable Long id,
                                                                                @Valid GroupFinder groupFinder, BindingResult result, Model model) throws Exception {
        model.addAttribute("user", UserInfo.getWrapper());
        if (result.hasErrors()) {
            return "addEditGroupFinderPage";
        }

        GroupFinder temp = groupFinderService.updateGroupFinder(id, groupFinder);

		return "redirect:/groupfinders/" + sub;
	}

    @RequestMapping("/groupfinders/{sub}/delete/{id}")
    public String deleteGroupFinder(@PathVariable(required = true)String sub, @PathVariable Long id, Model model) {
        model.addAttribute("user", UserInfo.getWrapper());
        groupFinderService.deleteGroupFinder(id);
        return "redirect:/groupfinders/" + sub;
    }


 }

