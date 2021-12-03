package com.ynov.tdspring.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "event")
public class Event {

    public String EVENT_USER = "USER";
    public String EVENT_USER_REQUEST = "USER_REQUEST";
    public String EVENT_PROJECT = "PROJECT";
    public String EVENT_RESEARCH = "RESEARCH";
    public String EVENT_COMMENT = "COMMENT";
    public String EVENT_LIKE = "LIKE";

    public String EVENT_ACTION_CREATE = "create";
    public String EVENT_ACTION_UPDATE = "update";
    public String EVENT_ACTION_DELETE = "delete";
    public String EVENT_ACTION_ACCEPT = "accepted";
    public String EVENT_ACTION_REQUEST_JOIN = "request to join";
    public String EVENT_ACTION_REFUSE = "refused";

    @Id
    @Column(name="id")
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotBlank
    @Column(name="title")
    private String title;

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

    @NotNull
    @NotBlank
    @ManyToOne
    @JoinColumn(name="author")
    private User author;

    @NotNull
    @NotBlank
    @JoinColumn(name="entity_id")
    private Object entityId;

    @NotNull
    @NotBlank
    @JoinColumn(name="entity")
    private String entity;

    // ------------------------ >

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Object getEntityId() {
        return entityId;
    }

    public void setEntityId(Object entityId) {
        this.entityId = entityId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
