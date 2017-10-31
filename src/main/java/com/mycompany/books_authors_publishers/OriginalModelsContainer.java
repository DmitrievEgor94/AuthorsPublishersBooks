package com.mycompany.books_authors_publishers;

import java.util.List;

public class AllModelsLists {
    List<Author> authors;
    List<Book> books;
    List<Publisher> publishers;

    public AllModelsLists(List<Author> authors, List<Book> books, List<Publisher> publishers) {
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
