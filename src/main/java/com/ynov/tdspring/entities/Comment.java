package com.ynov.tdspring.entities; 

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "comments")
public class Comment {
	@Id 
	@Column(name="id")
	@Type(type="uuid-char")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@NotNull
	@ManyToOne 
	@JoinColumn(name="author", nullable=false)
	private User author; 

	@NotNull
	@ManyToOne
	@JoinColumn(name="project", nullable=false)
	private Project project; 

	@NotNull
	@NotBlank
	@Column(name="message")
	private String message; 

	@NotNull
	@ManyToMany
	@JoinTable(name="likes", joinColumns= @JoinColumn(name="username"), inverseJoinColumns= @JoinColumn(name="id"))
	private List<User> likes

	public Comment () {}

	public Comment (User author, Project project, String message, List<User> likes) {
		this.setAuthor(author);
		this.setProject(project);
		this.setMessage(message);
		this.setLikes(likes);
	}

	public UUID getId() {
		return id; 
	}

	public void setId(UUID id) {
		this.id = id; 
	}

	public User getAuthor() {
		return author; 
	}

	public void setAuthor(User author) {
		this.author = author; 
	}

	public Project getProject() {
		return project; 
	}

	public void setProject(Project project) {
		this.project = project; 
	}

	public String getMessage() {
		return message; 
	}

	public void setMessage(String message) {
		this.message = message; 
	}

	public List<User> getLikes() {
		return likes; 
	}

	public void setLikes(List<User> likes) {
		this.likes = likes; 
	}
}