package com.mycompany.serializer;

import com.mycompany.books_authors_publishers.Author;
import com.mycompany.books_authors_publishers.Book;
import com.mycompany.books_authors_publishers.OriginalModelsContainer;
import com.mycompany.books_authors_publishers.Publisher;
import com.mycompany.books_authors_publishers.entities.AuthorEntity;
import com.mycompany.books_authors_publishers.entities.BookEntity;
import com.mycompany.books_authors_publishers.entities.PublisherEntity;
import com.mycompany.books_authors_publishers.entities.creators.AuthorEntitiesCreator;
import com.mycompany.books_authors_publishers.entities.creators.BookEntitiesCreator;
import com.mycompany.books_authors_publishers.entities.creators.PublisherEntitiesCreator;
import com.mycompany.books_authors_publishers.entities.restorators.AuthorsRestorator;
import com.mycompany.books_authors_publishers.entities.restorators.BooksRestorator;
import com.mycompany.books_authors_publishers.entities.restorators.PublishersRestorator;

import java.io.*;
import java.util.List;

public class JavaSerializer implements Serializer {

    @Override
    public void serializeObjects(List<Author> authors, List<Book> books, List<Publisher> publishers, String fileWithObjects) throws IOException {
        ObjectOutputStream out =
                new ObjectOutputStream(
                        new FileOutputStream(
                                new File(fileWithObjects)));

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
    public OriginalModelsContainer deserializeObject(String fileWithObjects) throws IOException, ClassNotFoundException {
        ObjectInputStream in =
                new ObjectInputStream(
                        new FileInputStream(
                                new File(fileWithObjects)));

        List<AuthorEntity> authorEntities = (List<AuthorEntity>) in.readObject();
        List<BookEntity> bookEntities = (List<BookEntity>) in.readObject();
        List<PublisherEntity> publisherEntities = (List<PublisherEntity>) in.readObject();

        List<Author> authors = AuthorsRestorator.getListOfAuthors(authorEntities);
        List<Book> books = BooksRestorator.getListOfBooks(bookEntities, authors, authorEntities);
        List<Publisher> publishers = PublishersRestorator.getListOfPublishers(publisherEntities, bookEntities, books);

        return new OriginalModelsContainer(authors, books, publishers);
    }
}
