package com.ynov.tdspring.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role {
	
	@Id 
	@Column(name="id")
	@Type(type="uuid-char")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	
	@NotNull
	@ManyToOne 
	@JoinColumn(name="user_id", nullable=false)
	private User user; 
	
	@NotNull
	@ManyToOne 
	@JoinColumn(name="project_id", nullable=false)
	private Project project; 
	
	@NotNull
	@NotBlank
	@Column(name="role")
	private String role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	} 
	
	
	
}