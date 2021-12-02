package com.ynov.tdspring.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "researchs")
public class Research {

    private final String STATUS_PENDING = "PENDING";
    private final String STATUS_ACCEPTED = "ACCEPTED";
    private final String STATUS_REFUSED = "REFUSED";

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
    @NotBlank
    @Column(name="status")
    private String status;

    // ------------------------------- >


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
