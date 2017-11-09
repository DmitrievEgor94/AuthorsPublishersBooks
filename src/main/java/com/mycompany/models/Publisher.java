package com.mycompany.models;

import java.util.List;

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
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Publisher publisher = (Publisher) obj;

        return this.name.equals(publisher.name) && this.books.equals(publisher.books);

    }
}
