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
        EXPERT, BEGINNER, INTERMEDIATE, ANY
    }

    public enum GameStyle {
        CASUAL, PROFESSIONAL;
    }

    @Id @GeneratedValue private Long id;


    private String title;
    private String gameTitle;
    private RequiredLevel requiredLevel;

    private int totalPlayers;
    private int currentPlayers;

    private String description;
    private String writerId;

    private String password;

    private GameStyle GameStyle;

    private boolean isPrivate = false;

    public GroupFinder() {}

    public GroupFinder(String title, String gameTitle, RequiredLevel requiredLevel, int totalPlayers, int currentPlayers, String description, String writerId) {
		this.title = title;
        this.gameTitle = gameTitle;
		this.requiredLevel = requiredLevel;
        this.totalPlayers = totalPlayers;
        this.currentPlayers = currentPlayers;
        this.description = description;
        this.writerId = writerId;
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

    public void setTitle(String title){
        this.title = title;
    }

	public String getGameTitle() {
		return this.gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}

    public RequiredLevel getRequiredLevel() {
		return this.requiredLevel;
	}

	public void setRequiredLevel(RequiredLevel requiredLevel) {
		this.requiredLevel = requiredLevel;
	}
    
    public com.cmpt276.gameinn.models.GroupFinder.GameStyle getGameStyle() {
		return this.GameStyle;
	}

	public void setGameStyle(GameStyle gameStyle) {
		this.GameStyle = gameStyle;
	}

    public int getTotalPlayers()
    {
        return this.totalPlayers;
    }

    public void setTotalPlayers(int totalPlyrs)
    {
        this.totalPlayers = totalPlyrs;
    }

    public int getCurrentPlayers()
    {
        return this.currentPlayers;
    }

    public void setCurrentPlayers(int crnt)
    {
        this.currentPlayers = crnt;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String despt)
    {
        this.description = despt;
    }

    public String getWriterID()
    {
        return this.writerId;
    }

    public void setWriterID(String writerId)
    {
        this.writerId = writerId;
    }

    
    public boolean getIsPrivate()
    {
        return this.isPrivate;
    }

    public void setIsPrivate(boolean isPrivate)
    {
        this.isPrivate = isPrivate;
    }


    @Override public int hashCode() {
		return Objects.hash(this.title, this.gameTitle, this.totalPlayers, this.currentPlayers,
			this.requiredLevel, this.GameStyle);
	}

	@Override public String toString() {
		return "Employee{" + "title=" + this.title + ", gametiitle='" + this.gameTitle +
			 ", totalPlayers='" + this.totalPlayers + ", currentPlayers='" + this.currentPlayers + ", required level='" +
			   this.requiredLevel + ", gameStyle='" + this.GameStyle + '\'' + '}';
	}
}


// GroupFinder
// ← → User (many to 1) "DONE"  ------
// - title (String) "DONE"------------
// - gameTitle (String)           -----------                                                     
// - requiredLevel (maybe Enum)-------------
// - gameStyle (maybe Enum) -------------
// - totalPlayers (int)----------------
// - currentPlayers (int)------------------
// - isPrivate (bool)----------------
// - status (maybe Enum)
// determined by isPrivate
// - password (String)------------------
// - description (String) ---------------------
// - writerId (String)--------------------------
//  This is from User for determining the user is poster or not
// - comments (List of Comment)
// For now, this entity is not mandatory. If you have enought time, try this
