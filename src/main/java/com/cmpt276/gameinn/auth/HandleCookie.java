package com.cmpt276.gameinn.auth;


import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class HandleCookie {
	public static final String COOKIE_NAME = "CURRENT_USER";

	public static String readCookie(HttpServletRequest request, String key) {
		return Arrays.stream(request.getCookies())
		  .filter(c -> key.equals(c.getName()))
		  .map(Cookie::getValue)
		  .map(Object::toString)
		  .findAny()
		  .orElse(null);
	}
}
