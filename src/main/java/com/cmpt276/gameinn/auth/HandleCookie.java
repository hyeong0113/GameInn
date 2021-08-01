package com.cmpt276.gameinn.auth;


import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class HandleCookie {
	public static final String COOKIE_NAME = "CURRENT_USER";

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String subId) {
		if (HandleCookie.readCookie(request, HandleCookie.COOKIE_NAME) == null) {
			Cookie cookie = new Cookie(HandleCookie.COOKIE_NAME, subId);
			cookie.setMaxAge(24 * 60 * 60);
			response.addCookie(cookie);
		}
	}

	public static String readCookie(HttpServletRequest request, String key) {
		return Arrays.stream(request.getCookies())
		  .filter(c -> key.equals(c.getName()))
		  .map(Cookie::getValue)
		  .map(Object::toString)
		  .findAny()
		  .orElse(null);
	}

	public static void deleteCookie(HttpServletResponse response, String key) {
		Cookie cookie = new Cookie(key, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}
