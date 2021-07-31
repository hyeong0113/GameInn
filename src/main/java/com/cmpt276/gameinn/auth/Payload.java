package com.cmpt276.gameinn.auth;


import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class Payload {
    private String sub;

    private String name;

	private String email;

	private String photo;

	private String role;

    public Payload() {
    }

    public Payload(OidcUser principal, String sub, String role) {
        this.sub = sub.substring(sub.lastIndexOf('|') + 1);
        this.name = principal.getClaims().get("name").toString();
		this.email = principal.getClaims().get("email").toString();
		this.photo = principal.getClaims().get("picture").toString();
        this.role = role;
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

    public String toString() {
		return "{" + "subId='" + this.sub + ", name='" + this.name + ", email='" + this.email + ", photo='" + this.photo
                    + ", role='" + this.role + '\'' + '}';
	}
}
