package com.ynov.tdspring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.ynov.tdspring.entities.*;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

	@Id 
	@Column(name="id")
	@Type(type="uuid-char")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
    @NotNull
    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    
    @NotNull
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;
   
    @Column(name = "github_token")
    private String github_token;
    
    @NotNull
    @NotBlank
    @JsonIgnore
    @Column(name = "password")
    private String password;
    
    
    @OneToMany(mappedBy = "id")
    private List<Issue> issues = new ArrayList<Issue>();

    @OneToMany(mappedBy = "user")
    private List<Role> roles = new ArrayList<Role>();
    
    // ------------------------ >

	  public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGithub_token() {
		return github_token;
	}

	public void setGithub_token(String github_token) {
		this.github_token = github_token;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}
  
    
}
