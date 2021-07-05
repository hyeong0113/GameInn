package com.cmpt276.gameinn.models;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import java.util.Objects;
import javax.persistence.ElementCollection;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    // subId - unique id after successful registration via auth0
    @NotNull
    private String sub;

    // @ElementCollection
    // private List<String> socialAccountsList;
    
    public User() {
    }

    public User(String sub) {
        this.sub = sub;
        // this.socialAccountsList = new ArrayList<String>();
    }

    public Long getId() {
        return this.id;
    }

    public String getSubId() {
        return this.sub;
    }

    public void setSubId(String sub) {
        this.sub = sub;
    }

    // public List<String> getsocialAccountsList() {
    //     return this.socialAccountsList;
    // }

    // public void setsocialAccountsList(List<String> socialAccountsList) {
    //     this.socialAccountsList = socialAccountsList;
    // }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (!(o instanceof User))
        return false;
        User user = (User) o;
      return Objects.equals(this.id, user.id) && Objects.equals(this.sub, user.sub);
    }
  
    @Override
    public int hashCode() {
      return Objects.hash(this.id, this.sub);
    }
  
    @Override
    public String toString() {
      return "Employee{" + "id=" + this.id + ", subId='" + this.sub + '\'' + '}';
    }
}
