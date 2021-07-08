package com.cmpt276.gameinn.models;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import java.util.Objects;

import javax.persistence.Column;
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

    @NotNull
    private String name;
  
    @NotNull
    private String email;

    @NotNull
    private String photo;

    private String about;

    private String role;

    @ElementCollection
    @Column(name = "accounts")
    private List<String> socialAccountsList;
    
    public User() {
    }

    public User(String sub, String name, String email, String photo, String role) {
        this.sub = sub;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.about = "";
        this.role = role;
        this.socialAccountsList = new ArrayList<String>();
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
      return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
      return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public List<String> getSocialAccountsList() {
        return this.socialAccountsList;
    }

    public void setSocialAccountsList(List<String> socialAccountsList) {
        this.socialAccountsList = socialAccountsList;
    }

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
      return Objects.hash(this.id, this.sub, this.name, this.email, this.photo, this.about);
    }
  
    @Override
    public String toString() {
      return "Employee{" + "id=" + this.id + ", subId='" + this.sub
                        + ", name='" + this.name
                        + ", email='" + this.email
                        + ", photo='" + this.photo
                        + ", about='" + this.about
                        + ", socialAccountsList='" + this.socialAccountsList +'\'' + '}';
    }
}
