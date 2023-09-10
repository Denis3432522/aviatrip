package com.example.aviatrip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
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
    @JsonIgnore
    private long balance;

    @Column
    @NotNull
    @Size(min=8, max = 120)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    public User() {}

    public User(String name, String surname, String email, long balance, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.balance = balance;
        this.password = password;
        this.role = role;
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
