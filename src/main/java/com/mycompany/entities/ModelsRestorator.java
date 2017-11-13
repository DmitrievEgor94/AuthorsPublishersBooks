package com.mycompany.entities;

import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.Publisher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelsRestorator {

    static public List<Author> getListOfAuthors(List<AuthorEntity> entities) {
        return entities.stream()
                .map(ModelsRestorator::getAuthor)
                .collect(Collectors.toList());
    }

    static private Author getAuthor(AuthorEntity entity) {
        return new Author(entity.getName(), entity.getDayOfBirthday(), entity.getDayOfDeath(), entity.getSex());
    }

    static public List<Book> getListOfBooks(List<BookEntity> entities, List<Author> authors, List<AuthorEntity> authorEntities) {

        if (entities == null) return null;

        Map<String, Author> mapNameAndAuthor = authors.stream()
                .collect(Collectors.toMap(Author::getName, a -> a));

        Map<Integer, Author> mapIdAuthor = authorEntities.stream()
                .collect(Collectors.toMap(AuthorEntity::getId, a -> mapNameAndAuthor.get(a.getName())));

        return entities.stream()
                .map(b -> getBook(b, mapIdAuthor))
                .collect(Collectors.toList());
    }

    static private Book getBook(BookEntity bookEntity, Map<Integer, Author> mapIdAuthor) {
        return new Book(bookEntity.getTitle(), bookEntity.getPublicationDate()
                , bookEntity.getAuthorsId().stream()
                .map(mapIdAuthor::get)
                .collect(Collectors.toList()));
    }

    static public List<Publisher> getListOfPublishers(List<PublisherEntity> entities, List<BookEntity> bookEntities, List<Book> books) {
        if (entities == null) return null;
        if (bookEntities == null) return null;

        Map<String, Book> mapTitleBook = books.stream()
                .collect(Collectors.toMap(Book::getTitle, b -> b));

        Map<Integer, Book> mapIdBook = bookEntities.stream()
                .collect(Collectors.toMap(BookEntity::getId, b -> mapTitleBook.get(b.getTitle())));


        return entities.stream()
                .map(e -> getPublisher(e, mapIdBook))
                .collect(Collectors.toList());
    }

    static private Publisher getPublisher(PublisherEntity entity, Map<Integer, Book> mapIdBook) {

        return new Publisher(entity.getName(),
                entity.getBooksId().stream()
                        .map(mapIdBook::get)
                        .collect(Collectors.toList()));
    }
}
