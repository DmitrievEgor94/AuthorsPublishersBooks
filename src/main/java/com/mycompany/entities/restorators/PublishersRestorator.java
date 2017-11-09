package com.mycompany.entities.restorators;

import com.mycompany.models.Book;
import com.mycompany.models.Publisher;
import com.mycompany.entities.BookEntity;
import com.mycompany.entities.PublisherEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PublishersRestorator {

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
