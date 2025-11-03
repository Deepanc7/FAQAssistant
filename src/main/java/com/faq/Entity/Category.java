package com.faq.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable=false, unique=true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable=false)
    private Instant createdAt = Instant.now();

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

