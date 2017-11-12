package com.mycompany.serializers;

import com.mycompany.entities.AuthorEntity;
import com.mycompany.entities.BookEntity;
import com.mycompany.entities.PublisherEntity;
import com.mycompany.entities.creators.AuthorEntitiesCreator;
import com.mycompany.entities.creators.BookEntitiesCreator;
import com.mycompany.entities.creators.PublisherEntitiesCreator;
import com.mycompany.entities.restorators.AuthorsRestorator;
import com.mycompany.entities.restorators.BooksRestorator;
import com.mycompany.entities.restorators.PublishersRestorator;
import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.Publisher;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JavaSerializer implements Serializer {

    @Override
    public void serializeObjects(List<Publisher> publishers, String fileWithObjects) throws IOException {
        ObjectOutputStream out =
                new ObjectOutputStream(
                        new FileOutputStream(
                                new File(fileWithObjects)));

        List<Book> books = publishers.stream()
                .map(Publisher::getBooks)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        List<Author> authors = books.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        List<AuthorEntity> authorEntities = AuthorEntitiesCreator.getListAuthorEntities(authors);
        List<BookEntity> bookEntities = BookEntitiesCreator.getListBookEntities(books, authorEntities);
        List<PublisherEntity> publisherEntities = PublisherEntitiesCreator.getListPublisherEntities(publishers, bookEntities);

        out.writeObject(authorEntities);
        out.writeObject(bookEntities);
        out.writeObject(publisherEntities);

        out.flush();
        out.close();
    }

    @Override
    public List<Publisher> deserializeObject(String fileWithObjects) throws IOException {

        ObjectInputStream in = null;

        try {

            in = new ObjectInputStream(
                    new FileInputStream(
                            new File(fileWithObjects)));

            List<AuthorEntity> authorEntities = (List<AuthorEntity>) in.readObject();
            List<BookEntity> bookEntities = (List<BookEntity>) in.readObject();
            List<PublisherEntity> publisherEntities = (List<PublisherEntity>) in.readObject();

            List<Author> authors = AuthorsRestorator.getListOfAuthors(authorEntities);
            List<Book> books = BooksRestorator.getListOfBooks(bookEntities, authors, authorEntities);

            return PublishersRestorator.getListOfPublishers(publisherEntities, bookEntities, books);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return null;
    }
}
