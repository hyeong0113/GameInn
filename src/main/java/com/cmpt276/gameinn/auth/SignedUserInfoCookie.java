package com.cmpt276.gameinn.auth;

import org.springframework.boot.web.servlet.server.Session.Cookie;

public class SignedUserInfoCookie extends Cookie {
    private Payload payload;

    public SignedUserInfoCookie() {
    }

    public SignedUserInfoCookie(Payload payload) {
        this.payload = payload;
    }

    public Payload getPayload() {
        return this.payload;
    }
}
