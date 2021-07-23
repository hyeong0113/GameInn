package com.cmpt276.gameinn.wrapper;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class UserWrapper {
    private String sub;

    private String name;

	private String email;

	private String photo;

    private String about;

	private String role;

    public UserWrapper() {
    }

    public UserWrapper(OidcUser principal) {
        this.name = principal.getClaims().get("name").toString();
		this.email = principal.getClaims().get("email").toString();
		this.photo = principal.getClaims().get("picture").toString();
    }


    public String getName() {
		return this.name;
	}

    public void setName(OidcUser principal) {
		this.name = principal.getClaims().get("name").toString();
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(OidcUser principal) {
		this.email = principal.getClaims().get("email").toString();
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(OidcUser principal) {
		this.photo = principal.getClaims().get("picture").toString();
	}

    public String getSubId() {
		return this.sub;
	}

	public void setSubId(String sub) {
		this.sub = sub;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

}

