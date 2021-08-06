package com.cmpt276.gameinn.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.cmpt276.gameinn.models.Clip;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.services.ClipService;
import com.cmpt276.gameinn.services.UserService;
import com.cmpt276.gameinn.auth.HandleCookie;

@Controller
public class ClipController {
    @Autowired private ClipService clipService;
    @Autowired private UserService userService;

    private String urlError = "Source URL is not valid. Note: You should add http:// or https://";
    private String emptyURL = "";

    private boolean isValidateURL(String url) {
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);

        if (urlValidator.isValid(url)) {
            return true;
        }

        return false;
    }

	@GetMapping(value = {"/clips", "/clips/{sub}"}) public String clipListPage(@PathVariable(required = false)String sub, Model model, HttpServletRequest request,
                                                                                                                        @AuthenticationPrincipal OidcUser principal) {
        String url="";
        if (principal != null) {
            model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
            url=String.format("/clips/%s/addEdit", HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME));
        }
        else {
            url="/oauth2/authorization/auth0";
        }
        model.addAttribute("url", url);
        model.addAttribute("current_time", new Date());
        model.addAttribute("clip_list", clipService.getClips());

		return "clipList";
	}

    @GetMapping("/clips/{sub}/addEdit")
    public String showAddEditClipPageForCreate(@PathVariable(required = true)String sub, Clip clip, Model model, HttpServletRequest request) {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        model.addAttribute("showAlert", false);
        model.addAttribute("errorMessage", emptyURL);

        String addUrl = "/clips/" + sub + "/addEdit/add";
        model.addAttribute("url", addUrl);

        return "addEditClipPage";
    }
    
    // Assure User is logged in and have an authorization to create
    @PostMapping("/clips/{sub}/addEdit/add") public String addClip(@PathVariable(required = true)String sub, @Valid Clip clip, BindingResult result, Model model, HttpServletRequest request) {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));

        if (result.hasErrors() || !isValidateURL(clip.getSourceLink())) {
            model.addAttribute("showAlert", true);
            model.addAttribute("errorMessage", urlError);
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
    public String showAddEditClipPageForEdit(@PathVariable(required = true)String sub, @PathVariable Long id, Model model, HttpServletRequest request) {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        model.addAttribute("clip", clipService.getClipByID(id));
        String editUrl = "/clips/" + sub + "/addEdit/edit/" + id;
        model.addAttribute("url", editUrl);
        return "addEditClipPage";
    }

    @RequestMapping("/clips/{sub}/addEdit/edit/{id}") public String editClip(@PathVariable(required = true)String sub, @PathVariable Long id,
                                                                            @Valid Clip clip, BindingResult result, Model model,
                                                                            HttpServletRequest request) throws Exception {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        if (result.hasErrors()) {
            return "addEditClipPage";
        }

        Clip temp = clipService.updateClip(id, clip);

		return "redirect:/clips/" + sub;
	}

    @RequestMapping("/clips/{sub}/delete/{id}")
    public String deleteClip(@PathVariable(required = true)String sub, @PathVariable Long id, Model model, HttpServletRequest request, @AuthenticationPrincipal OidcUser principal) {
        model.addAttribute("user", userService.getUserBySub(HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME)));
        clipService.deleteClip(id);
        return "redirect:/clips/" + sub;
    }
}
