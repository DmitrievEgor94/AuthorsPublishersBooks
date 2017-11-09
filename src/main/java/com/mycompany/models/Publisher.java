package com.mycompany.models;

import java.util.List;
import java.util.Objects;

public class Publisher {
    private String name;
    private List<Book> books;

    public Publisher() {
    }

    public Publisher(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, books);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Publisher publisher = (Publisher) obj;

        return ((this.name == null) ? (publisher.name == null) : (this.name.equals(publisher.name)))
                && ((this.books == null) ? (publisher.books == null) : (this.books.equals(publisher.books)));

    }
}
