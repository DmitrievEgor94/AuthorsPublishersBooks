package com.mycompany.models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Author {

    private String name;
    private LocalDate dayOfBirthday;
    private LocalDate dayOfDeath;
    private Sex sex;

    public enum Sex {MALE, FEMALE}

    public Author() {
    }

    public Author(String name, LocalDate dayOfBirthday, LocalDate dayOfDeath, Sex sex) {
        this.name = name;
        this.dayOfBirthday = dayOfBirthday;
        this.dayOfDeath = dayOfDeath;
        this.sex = sex;
    }

    public LocalDate getDayOfBirthday() {
        return dayOfBirthday;
    }

    public Optional<LocalDate> getDayOfDeath() {
        return Optional.ofNullable(dayOfDeath);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDayOfBirthday(LocalDate dayOfBirthday) {
        this.dayOfBirthday = dayOfBirthday;
    }

    public void setDayOfDeath(LocalDate dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dayOfBirthday, dayOfDeath, sex);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (obj.getClass() != this.getClass()) return false;

        Author author = (Author) obj;

        return  Objects.equals(name, author.name)
                && Objects.equals(dayOfBirthday, author.dayOfBirthday)
                && Objects.equals(dayOfDeath, author.dayOfDeath)
                && Objects.equals(sex, author.sex);

    }
}
