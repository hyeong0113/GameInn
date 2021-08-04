package com.cmpt276.gameinn.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.cmpt276.gameinn.constant.EnumCollection.Platform;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Table;

@Entity @Table(name = "clips") public class Clip {
	@Id @GeneratedValue private Long id;

	@NotNull private String title;

	@NotNull private String gameTitle;

	@ElementCollection @Column(name = "tag_list") private List<String> tags;

	@NotNull private Platform platform;

	@NotNull private String sourceLink;

	@NotNull private Date postedTime;

	@ManyToOne @JoinColumn(name = "user_id") @OnDelete(action =
	OnDeleteAction.CASCADE) private User RUser;

	public Clip() {}

	public Clip(String title, String gameTitle, List<String> tags, Platform
		platform, String sourceLink, User user) {
		this.title = title;
		this.gameTitle = gameTitle;
		this.tags = tags;
		this.platform = platform;
		this.sourceLink = sourceLink;
		this.postedTime = new Date();
		this.RUser = user;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGameTitle() {
		return this.gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Platform getPlatform() {
		return this.platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public String getSourceLink() {
		return this.sourceLink;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}

	public Date getPostedTime() {
		return this.postedTime;
	}

	public void setPostedTime(Date postedTime) {
		this.postedTime = postedTime;
	}

	public User getUser() {
		return this.RUser;
	}

	public void setUser(User user) {
		this.RUser = user;
	}

	@Override public int hashCode() {
		return Objects.hash(this.title, this.gameTitle, this.tags,
			this.sourceLink, this.postedTime);
	}

	@Override public String toString() {
		return "Employee{" + "title=" + this.title + ", gametiitle='" +
			   this.gameTitle + ", tags='" + this.tags + ", platform='" +
			   this.platform.toString() + ", sourceLink='" + this.sourceLink +
			   ", postedTime='" + this.postedTime + '\'' + '}';
	}
}
