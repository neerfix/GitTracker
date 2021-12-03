package com.ynov.tdspring.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users_research_requests")
public class UserResearchRequest {

    public String STATUS_PENDING = "PENDING";
    public String STATUS_ACCEPTED = "ACCEPTED";
    public String STATUS_REFUSED = "REFUSED";

    @Id
    @Column(name="id")
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotBlank
    @Column(name = "status")
    private String status;

    @NotNull
    @NotBlank
    @ManyToOne
    @JoinColumn(name="users", nullable=false)
    private User user;

    @NotNull
    @NotBlank
    @ManyToOne
    @JoinColumn(name="project", nullable=false)
    private Project project;
    
    public Research getResearch() {
		return research;
	}

	public void setResearch(Research research) {
		this.research = research;
	}

	@NotNull
    @NotBlank
    @ManyToOne
    @JoinColumn(name="research", nullable=false)
    private Research research;

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

    // ------------------------ >

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
