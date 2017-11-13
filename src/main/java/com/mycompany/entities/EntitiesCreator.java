package com.mycompany.entities;

import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.Publisher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EntitiesCreator {
    static public List<AuthorEntity> getListAuthorEntities(List<Author> authors) {

        if (authors == null) return null;

        AtomicInteger counter = new AtomicInteger(0);

        return authors.stream()
                .map(author -> new AuthorEntity(author, counter.incrementAndGet()))
                .collect(Collectors.toList());
    }

    static public List<BookEntity> getListBookEntities(List<Book> books, List<AuthorEntity> authorEntities) {

        if (books == null) return null;

        Map<String, Integer> mapAuthorNameId = authorEntities.stream()
                .collect(Collectors.toMap(AuthorEntity::getName, AuthorEntity::getId));

        AtomicInteger counter = new AtomicInteger(0);

        return books.stream()
                .map(b -> new BookEntity(b, mapAuthorNameId, counter.incrementAndGet()))
                .collect(Collectors.toList());
    }

    static public List<PublisherEntity> getListPublisherEntities(List<Publisher> publishers
            , List<BookEntity> bookEntities) {

        if (publishers == null || bookEntities == null) return null;

        AtomicInteger counter = new AtomicInteger(0);

        Map<String, Integer> mapBooksTitleId = bookEntities.stream()
                .collect(Collectors.toMap(BookEntity::getTitle, BookEntity::getId));

        return publishers.stream()
                .map(p -> new PublisherEntity(p, mapBooksTitleId, counter.incrementAndGet()))
                .collect(Collectors.toList());
    }
}
