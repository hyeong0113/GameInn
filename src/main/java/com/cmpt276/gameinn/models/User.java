package com.cmpt276.gameinn.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Table;

@Entity @Table(name = "users") public class User {
	@Id @GeneratedValue private Long id;

	// subId - unique id after successful registration via auth0
	@NotNull private String sub;

	@NotNull private String name;

	@NotNull private String picture;

	@NotNull private String email;

	private String about;

	private String role;

	@ElementCollection @Column(name = "accounts")
	private List<String> socialAccountsList;

	@OneToMany(mappedBy = "RUser")
    private List<GroupFinder> groupFinders;

	@OneToMany(mappedBy = "RUser")
    private List<Clip> clips;

	@OneToMany(mappedBy = "groupFinder")
    private List<Comment> comments;

	public User() {}

	public User(String sub, String role, String name, String picture, String email) {
		this.sub = sub;
		this.about = "";
		this.role = role;
		this.name = name;
		this.picture = picture;
		this.email = email;
		this.socialAccountsList = new ArrayList<String>();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<String> getSocialAccountsList() {
		return this.socialAccountsList;
	}

	public void setSocialAccountsList(List<String> socialAccountsList) {
		this.socialAccountsList = socialAccountsList;
	}

	public List<GroupFinder> getGroupFinders() {
		return this.groupFinders;
	}

	public void setGroupFinders(List<GroupFinder> groupFinders) {
		this.groupFinders = groupFinders;
	}

	public List<Clip> getClips() {
		return this.clips;
	}

	public void setClips(List<Clip> clips) {
		this.clips = clips;
	}



	@Override public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof User))
			return false;

		User user = (User)o;
		return Objects.equals(this.id, user.id) && Objects.equals(this.sub,
			user.sub);
	}

	@Override public int hashCode() {
		return Objects.hash(this.id, this.sub, this.role);
	}

	@Override public String toString() {
		return "{" + "id=" + this.id + ", subId='" + this.sub + ", role='" + this.role
					+ ", email='" + this.email + ", picture='" + this.picture + '\'' + '}';
	}
}
