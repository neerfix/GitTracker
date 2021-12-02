package com.ynov.tdspring.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @Column(name="id")
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotBlank
    @Column(name="name")
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name="author", nullable=false)
    private User author;

    @NotNull
    @NotBlank
    @Column(name="description")
    private String description;

    @NotNull
    @NotBlank
    @FutureOrPresent
    @Column(name="created_at")
    private Date createdAt;

    @NotNull
    @NotBlank
    @FutureOrPresent
    @Column(name="closed_at")
    private Date closedAt;

    @NotNull
    @ManyToMany
    @Min(value = 1)
    @Max(value = 16)
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "project"))
    private List<User> participants;

    @NotNull
    @NotBlank
    @Min(value = 1)
    @Column(name="nb_patients")
    private int nbPatients = 1;

    @NotNull
    @NotBlank
    @Min(value = 1)
    @Max(value = 16)
    @Column(name="nb_total_patients")
    private int nbTotalPatients = 16;

    @NotNull
    @OneToMany
    @JoinTable(name = "comments",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "comments"))
    private List<User> comments;

    @Column(name="keywords")
    private String[] keywords;

    @OneToMany
    @JoinTable(name = "researchs",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "researchs"))
    private List<User> researchs;

    @OneToMany
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "followers"))
    private List<User> followers;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public int getNbPatients() {
        return nbPatients;
    }

    public void setNbPatients(int nbPatients) {
        this.nbPatients = nbPatients;
    }

    public int getNbTotalPatients() {
        return nbTotalPatients;
    }

    public void setNbTotalPatients(int nbTotalPatients) {
        this.nbTotalPatients = nbTotalPatients;
    }

    public List<User> getComments() {
        return comments;
    }

    public void setComments(List<User> comments) {
        this.comments = comments;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public List<User> getResearchs() {
        return researchs;
    }

    public void setResearchs(List<User> researchs) {
        this.researchs = researchs;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }
}
