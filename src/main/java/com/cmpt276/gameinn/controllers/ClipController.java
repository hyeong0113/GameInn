package com.cmpt276.gameinn.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.cmpt276.gameinn.models.Clip;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.ClipService;
import com.cmpt276.gameinn.services.UserService;
import com.cmpt276.gameinn.constant.UserInfo;

@Controller
public class ClipController {
    @Autowired private ClipService clipService;
    @Autowired private UserService userService;

	@GetMapping(value = {"/clips", "/clips/{sub}"}) public String clipListPage(@PathVariable(required = false)String sub, Model model) {
		model.addAttribute("user", UserInfo.getWrapper());
		model.addAttribute("clip_list", clipService.getClips());

		return "clipList";
	}

    @GetMapping("/clips/{sub}/addEdit")
    public String showAddEditClipPageForCreate(@PathVariable(required = true)String sub, Clip clip, Model model) {
        model.addAttribute("user", UserInfo.getWrapper());
        return "addEditClipPage";
    }
    
    // Assure User is logged in and have an authorization to create
    @PostMapping("/clips/{sub}/addEdit/add") public String addClip(@PathVariable(required = true)String sub, @Valid Clip clip, BindingResult result, Model model) {
        model.addAttribute("user", UserInfo.getWrapper());

        if (result.hasErrors()) {
            return "addEditClipPage";
        }

        User user = userService.getUserBySub(sub);
        Clip temp = clipService.addClip(clip, user);

		return "redirect:/clips/" + sub;
	}

    // Not necessary for now
    // @GetMapping(value = {"/clips/{id}", "/clips/{sub}/{id}"}) public String getRectangle(@PathVariable(required = false) String sub, @PathVariable Long id, Model model) {
    //     model.addAttribute("clip", clipService.getClipByID(id));
    //     return "";
    // }

    @GetMapping("/clips/{sub}/addEdit/{id}")
    public String showAddEditClipPageForEdit(@PathVariable(required = true)String sub, @PathVariable Long id, Model model) {
        model.addAttribute("user", UserInfo.getWrapper());
        model.addAttribute("clip", clipService.getClipByID(id));
        return "addEditClipPage";
    }

    @RequestMapping("/clips/{sub}/addEdit/edit/{id}") public String editClip(@PathVariable(required = true)String sub, @PathVariable Long id,
                                                                            @Valid Clip clip, BindingResult result, Model model) throws Exception {
        model.addAttribute("user", UserInfo.getWrapper());
        if (result.hasErrors()) {
            return "addEditClipPage";
        }

        Clip temp = clipService.updateClip(id, clip);

		return "redirect:/clips/" + sub;
	}

    @RequestMapping("/clips/{sub}/delete/{id}")
    public String deleteClip(@PathVariable(required = true)String sub, @PathVariable Long id, Model model) {
        model.addAttribute("user", UserInfo.getWrapper());
        clipService.deleteClip(id);
        return "redirect:/clips/" + sub;
    }
}
