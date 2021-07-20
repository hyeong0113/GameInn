package com.cmpt276.gameinn.security;

import org.slf4j.MDC;
import org.springframework.ui.Model;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;


public class AuthenticationProvider {
    public static String USER_SUB = "sub";

    public static String USER_ROLE = "https://gameinn:us:auth0:com/api/v2/roles";

    public static String USER_EMAIL = "email";

    public static String USER_PICTURE = "picture";

    public static String USER_NAME = "name";


    public static String get(String keyword) {
        return MDC.get(keyword);
    }

    public static void storeUserInfo(OidcUser principal) {
        String sub = principal.getClaims().get(AuthenticationProvider.USER_SUB).toString();
        MDC.put(AuthenticationProvider.USER_SUB, sub.substring(sub.lastIndexOf('|') + 1));
        MDC.put(AuthenticationProvider.USER_ROLE, principal.getClaims().get(AuthenticationProvider.USER_ROLE).toString());
        MDC.put(AuthenticationProvider.USER_EMAIL, principal.getClaims().get(AuthenticationProvider.USER_EMAIL).toString());
        MDC.put(AuthenticationProvider.USER_PICTURE, principal.getClaims().get(AuthenticationProvider.USER_PICTURE).toString());
        MDC.put(AuthenticationProvider.USER_NAME, principal.getClaims().get(AuthenticationProvider.USER_NAME).toString());
    }

    public static void fetchURLInfo(Model model) {
        model.addAttribute("user_sub_ID", AuthenticationProvider.get(AuthenticationProvider.USER_SUB));
        model.addAttribute("user_role", AuthenticationProvider.get(AuthenticationProvider.USER_ROLE));
        model.addAttribute("user_email", AuthenticationProvider.get(AuthenticationProvider.USER_EMAIL));
        model.addAttribute("user_photo", AuthenticationProvider.get(AuthenticationProvider.USER_PICTURE));
        model.addAttribute("user_name", AuthenticationProvider.get(AuthenticationProvider.USER_NAME));
    }

    public static void clearStoredUserInfo() {
        MDC.clear();
    }
}
