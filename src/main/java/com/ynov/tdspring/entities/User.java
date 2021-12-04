package com.ynov.tdspring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.ynov.tdspring.entities.*;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @NotNull
    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @NotBlank
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @NotBlank
    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @NotNull
    @NotBlank
    @Column(name = "role")
    private String role;

    @NotNull
    @NotBlank
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @NotNull
    @NotBlank
    @FutureOrPresent
    @Column(name="created_at")
    private Date createdAt;

    @NotNull
    @NotBlank
    @FutureOrPresent
    @Column(name="updated_at")
    private Date updateAt;

    @ManyToMany(mappedBy="likes")
    private List<Comment> likedComments;

    @OneToMany(mappedBy = "researchs")
    private List<Research> researchs = new ArrayList<Research>();
    
    // ------------------------ >

	  public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
  
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
  
    public List<Comment> getLikedComments() {
		  return likedComments;
	  }

	  public void setLikedComments(List<Comment> likedComments) {
		  this.likedComments = likedComments;
	  }
  
    public List<Research> getResearchs() {
		  return researchs;
	  }

	  public void setResearchs(List<Research> orders) {
		  this.researchs = orders;
	  }
}
