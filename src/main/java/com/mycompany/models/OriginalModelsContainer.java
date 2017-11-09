package com.mycompany.models;

import java.util.List;

public class OriginalModelsContainer {
    private List<Author> authors;
    private List<Book> books;
    private List<Publisher> publishers;

    public OriginalModelsContainer(List<Author> authors, List<Book> books, List<Publisher> publishers) {
        this.authors = authors;
        this.books = books;
        this.publishers = publishers;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }
}
