package com.example.daily_notepad;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime dueDate;

    @Transient // Указываем, что эти поля не должны сохраняться в базе данных
    private String formattedCreateDate;
    @Transient
    private String formattedDueDate;

    public String getFormattedCreateDate() {
        return formattedCreateDate;
    }

    public void setFormattedCreateDate(String formattedCreateDate) {
        this.formattedCreateDate = formattedCreateDate;
    }

    public String getFormattedDueDate() {
        return formattedDueDate;
    }

    public void setFormattedDueDate(String formattedDueDate) {
        this.formattedDueDate = formattedDueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
