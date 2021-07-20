package com.cmpt276.gameinn.controllers;

import java.util.List;

import com.cmpt276.gameinn.models.GroupFinder;
import com.cmpt276.gameinn.services.GroupFinderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupFinderController {
    @Autowired private GroupFinderService service;

    @GetMapping("/groupfinders") public List<GroupFinder> groupFinders() {
		return service.getGroupFinders();
	}

    @PostMapping("/groupfinders/create") public GroupFinder createGroupFinder(@RequestParam GroupFinder groupFinder) {
		return service.addGroupFinder(groupFinder.getTitle(), groupFinder.getRequiredLevel());
	}
}
