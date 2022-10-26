package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Post {
    private static final AtomicInteger COUNT = new AtomicInteger();
    private int id;
    private String name;
    private String description;
    private LocalDateTime created = LocalDateTime.now();

    public Post() {
    }

    public Post(String name, String description) {
        this.id = COUNT.incrementAndGet();
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = COUNT.incrementAndGet();
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
