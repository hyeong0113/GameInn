package com.cmpt276.gameinn.models;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.cmpt276.gameinn.constant.EnumCollection.GameStyle;
import com.cmpt276.gameinn.constant.EnumCollection.RequiredLevel;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Table;

@Entity @Table(name = "groupFinders")
public class GroupFinder {

    @Id @GeneratedValue private Long id;

    @NotNull
    private String title;

    @NotNull
    private String gameTitle;

    @NotNull
    private RequiredLevel requiredLevel;

    @NotNull
    private int totalPlayers;

    @NotNull
    private int currentPlayers;

    @NotNull
    private GameStyle gameStyle;

    private String description;

    private String password;

    private boolean isPrivate = false;

    @ManyToOne
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User RUser;

    public GroupFinder() {}

    public GroupFinder(String title, String gameTitle, GameStyle gameStyle, RequiredLevel requiredLevel,
                    int totalPlayers, int currentPlayers, String description, String password, User user) {
		this.title = title;
        this.gameTitle = gameTitle;
        this.gameStyle = gameStyle;
		this.requiredLevel = requiredLevel;
        this.totalPlayers = totalPlayers;
        this.currentPlayers = currentPlayers;
        this.description = description;
        this.RUser = user;
        this.password = password;
        if (password.isEmpty()) {
            this.isPrivate = true;
        }
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
    
    public GameStyle getGameStyle() {
		return this.gameStyle;
	}

	public void setGameStyle(GameStyle gameStyle) {
		this.gameStyle = gameStyle;
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

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String despt)
    {
        this.description = despt;
    }
    
    public boolean getIsPrivate()
    {
        return this.isPrivate;
    }

    public void setIsPrivate(boolean isPrivate)
    {
        this.isPrivate = isPrivate;
    }

    public User getUser()
    {
        return this.RUser;
    }

    public void setUser(User user)
    {
        this.RUser = user;
    }

    
    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }


    @Override public int hashCode() {
		return Objects.hash(this.title, this.gameTitle, this.totalPlayers, this.currentPlayers,
			this.requiredLevel, this.gameStyle);
	}

	@Override public String toString() {
		return "Employee{" + "title=" + this.title + ", gametiitle='" + this.gameTitle +
			 ", totalPlayers='" + this.totalPlayers + ", currentPlayers='" + this.currentPlayers + ", required level='" +
			   this.requiredLevel + ", gameStyle='" + this.gameStyle + '\'' + '}';
	}
}
