package com.cmpt276.gameinn.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String level;

    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User RUser;

    @ManyToOne
    @JoinColumn(name="groupFinder_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GroupFinder groupFinder;

    @NotNull
    private Date postedTime;
    
    public Comment() {}

    public Comment(String level, String content, User user, GroupFinder groupFinder) {
        this.level = level;
        this.content = content;
        this.postedTime = new Date();
        this.RUser = user;
        this.groupFinder = groupFinder;
    }

    public Long getId() {
        return this.id;
    }

    public String getLevel() {
        return this.level;
    }

    public String getContent() {
        return this.content;
    }

    public User getUser()
    {
        return this.RUser;
    }

    public Date getPostedTime()
    {
        return this.postedTime;
    }

    public GroupFinder getGroupFinder()
    {
        return this.groupFinder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void setUser(User user)
    {
        this.RUser = user;
    }

    public void setGroupFinder(GroupFinder groupFinder)
    {
        this.groupFinder = groupFinder;
    }

    public void setPostedTime(Date postedTime)
    {
        this.postedTime = postedTime;
    }
}
