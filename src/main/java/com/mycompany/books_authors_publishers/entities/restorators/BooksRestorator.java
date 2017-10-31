package com.mycompany.books_authors_publishers.entities_creators_and_restorators;

import com.mycompany.books_authors_publishers.Author;
import com.mycompany.books_authors_publishers.Book;
import com.mycompany.books_authors_publishers.enteties_for_serializing.AuthorEntity;
import com.mycompany.books_authors_publishers.enteties_for_serializing.BookEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BooksRestorator {

    static public List<Book> getListOfBooks(List<BookEntity> entities, List<Author> authors, List<AuthorEntity> authorEntities) {

        if (entities == null) return null;

        Map<String, Author> mapNameAndAuthor = authors.stream()
                .collect(Collectors.toMap(Author::getName, a -> a));

        Map<Integer, Author> mapIdAuthor = authorEntities.stream()
                .collect(Collectors.toMap(AuthorEntity::getId, a -> mapNameAndAuthor.get(a.getName())));

        return entities.stream()
                .map(b->getBook(b,mapIdAuthor))
                        .collect(Collectors.toList());
    }

    static private Book getBook(BookEntity bookEntity, Map<Integer, Author> mapIdAuthor) {
        return new Book(bookEntity.getTitle(), bookEntity.getPublicationDate()
                , bookEntity.getAuthorsId().stream()
                .map(mapIdAuthor::get)
                .collect(Collectors.toList()));
    }
}
