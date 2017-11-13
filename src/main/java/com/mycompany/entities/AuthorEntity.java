package com.mycompany.entities;

import com.mycompany.models.Author;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class AuthorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private LocalDate dayOfBirthday;
    private LocalDate dayOfDeath;
    private Author.Sex sex;

    AuthorEntity(Author author, int id) {
        this.name = author.getName();
        this.dayOfBirthday = author.getDayOfBirthday();
        this.dayOfDeath = author.getDayOfDeath().orElse(null);
        this.sex = author.getSex();
        this.id = id;
    }

    public AuthorEntity(Integer id, String name, LocalDate dayOfBirthday, LocalDate dayOfDeath, Author.Sex sex) {
        this.id = id;
        this.name = name;
        this.dayOfBirthday = dayOfBirthday;
        this.dayOfDeath = dayOfDeath;
        this.sex = sex;
    }

    public int getId() {

        return id;
    }

    public Author.Sex getSex() {
        return sex;
    }

    public LocalDate getDayOfDeath() {

        return dayOfDeath;
    }

    public LocalDate getDayOfBirthday() {

        return dayOfBirthday;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (obj.getClass() != this.getClass()) return false;

        AuthorEntity entity = (AuthorEntity) obj;

        return Objects.equals(id, entity.id)
                && Objects.equals(name, entity.name)
                && Objects.equals(dayOfBirthday, entity.dayOfBirthday)
                && Objects.equals(dayOfDeath, entity.dayOfDeath)
                && Objects.equals(sex, entity.sex);

    }
}
