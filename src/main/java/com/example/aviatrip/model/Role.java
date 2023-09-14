package com.example.aviatrip.model;

import com.example.aviatrip.enumeration.Roles;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Column(name = "role_id")
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false, unique = true)
    private Roles name;

    public Role() {}

    public Role(Roles name) {
        this.name = name;
    }

    public Roles getName() {
        return name;
    }

    public void setName(Roles role) {
        this.name = role;
    }
}
