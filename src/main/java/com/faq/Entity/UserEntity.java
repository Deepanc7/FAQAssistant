package com.faq.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(unique=true, nullable=false)
    private String username;

    @Column(name="display_name")
    private String displayName;

    private String email;

    @Column(name="created_at", nullable=false)
    private Instant createdAt = Instant.now();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

