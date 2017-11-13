package com.mycompany.entities;

import com.mycompany.models.Book;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private LocalDate publicationDate;
    private List<Integer> authorsId;

    BookEntity(Book book, Map<String, Integer> mapWithAuthorsNames, int id) {
        this.title = book.getTitle();

        this.publicationDate = book.getPublicationDate();

        this.authorsId = book.getAuthors().stream()
                .map(author -> mapWithAuthorsNames.get(author.getName()))
                .collect(Collectors.toList());
        this.id = id;
    }

    public BookEntity(Integer id, String title, LocalDate publicationDate, List<Integer> authorsId) {
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.authorsId = authorsId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public List<Integer> getAuthorsId() {
        return authorsId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        BookEntity entity = (BookEntity) obj;

        return Objects.equals(id, entity.id)
                && Objects.equals(title, entity.title)
                && Objects.equals(publicationDate, entity.publicationDate)
                && Objects.equals(authorsId, entity.authorsId);

    }
}
