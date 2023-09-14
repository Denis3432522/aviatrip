package com.example.aviatrip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Column(name = "user_id")
    @Id
    @GeneratedValue
    private long id;

    @Column
    @NotNull
    @Pattern(regexp = "[A-Za-z]{2,32}")
    private String name;

    @Column
    @NotNull
    @Pattern(regexp = "[A-Za-z]{2,32}")
    private String surname;

    @Column(unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    @Email
    private String email;

    @Column
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long balance;

    @Column
    @NotNull
    @Size(min=8, max = 120)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public User() {}

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
