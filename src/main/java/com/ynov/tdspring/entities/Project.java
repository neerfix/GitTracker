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
    @JoinColumn(name="id", nullable=false)
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
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<User> participants;

    // ------------------------ >


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

}
