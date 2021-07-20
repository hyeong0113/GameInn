package com.cmpt276.gameinn.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Table;



@Entity @Table(name = "groupFinders")
public class GroupFinder {
    
    public enum RequiredLevel {
        EXPERT, BEGINNER, INTERMEDIATE
    }

    @Id @GeneratedValue private Long id;


    private String title;

    private RequiredLevel requiredLevel;


    public GroupFinder() {}

    public GroupFinder(String title, RequiredLevel requiredLevel) {
		this.title = title;
		this.requiredLevel = requiredLevel;
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

    public RequiredLevel getRequiredLevel() {
		return this.requiredLevel;
	}

	public void setRequiredLevel(RequiredLevel requiredLevel) {
		this.requiredLevel = requiredLevel;
	}
}



// GroupFinder
// ← → User (many to 1)
// - title (String)
// - gameTitle (String)
// - requiredLevel (maybe Enum)
// - gameStyle (maybe Enum)
// - totalPlayers (int)
// - currentPlayers (int)
// - isPrivate (bool)
// - status (maybe Enum)
// determined by isPrivate
// - password (String)
// - description (String)
// - writerId (String)
//  This is from User for determining the user is poster or not
// - comments (List of Comment)
// For now, this entity is not mandatory. If you have enought time, try this
