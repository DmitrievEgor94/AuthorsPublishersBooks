package com.mycompany.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Book {

    private String title;
    private LocalDate publicationDate;
    private List<Author> authors;

    public Book() {
    }

    public Book(String title, LocalDate publicationDate, List<Author> authors) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.authors = authors;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public List<Author> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publicationDate, authors);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Book book = (Book) obj;

        return Objects.equals(title, book.title)
                && Objects.equals(publicationDate, book.publicationDate)
                && Objects.equals(authors, book.authors);


    }
}
