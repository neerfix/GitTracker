package com.ynov.tdspring.entities; 

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;
import com.ynov.tdspring.entities.Issue;

@Entity
@Table(name = "comment")
public class Comment {
	@Id 
	@Column(name="id")
	@Type(type="uuid-char")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@NotNull
	@ManyToOne 
	@JoinColumn(name="author_id", nullable=false)
	private User author; 

	@NotNull
	@ManyToOne
	@JoinColumn(name="issue_id", nullable=false)
	private Issue issue; 

	@NotNull
	@NotBlank
	@Column(name="content")
	private String content; 

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


	public Comment () {}

	public Comment (User author, Issue issue, String message, List<User> likes) {
		this.setAuthor(author);
		this.setIssue(issue);
		this.setContent(message);
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


	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue2) {
		this.issue = issue2;
	}

	public String getContent() {
		return content; 
	}

	public void setContent(String message) {
		this.content = message; 
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
	
	
}