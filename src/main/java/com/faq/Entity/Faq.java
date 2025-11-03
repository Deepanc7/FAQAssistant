package com.faq.Entity;

import jakarta.persistence.*;
import org.apache.catalina.User;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "faqs")
public class Faq {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String question;

    @Column(length = 4000)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "faq_tags",
            joinColumns = @JoinColumn(name = "faq_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserEntity updatedBy) {
        this.updatedBy = updatedBy;
    }
}

