package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String desc;
    private boolean visible;
    private City city;
    private byte[] photo;
    private LocalDateTime created = LocalDateTime.now();

    public Candidate() {
    }

    public Candidate(int id, String name, String desc, byte[] photo) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.photo = photo;
    }

    public Candidate(int id, String name, City city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public Candidate(int id, String name, String desc, boolean visible, City city, byte[] photo, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.visible = visible;
        this.city = city;
        this.photo = photo;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
