package com.ynov.tdspring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "participants")
    private List<Exit> exits;

    @NotNull
    @NotBlank
    @Column(name = "role")
    private String role;

    @NotNull
    @NotBlank
    @JsonIgnore
    @Column(name = "password")
    private String password;

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

    public List<Exit> getExits() {
        return exits;
    }

    public void setExits(List<Exit> exits) {
        this.exits = exits;
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
}
