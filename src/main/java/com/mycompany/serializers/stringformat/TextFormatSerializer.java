package com.mycompany.serializers.stringformat;

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
import com.mycompany.serializers.Serializer;
import com.mycompany.serializers.stringformat.readers.AuthorsReader;
import com.mycompany.serializers.stringformat.readers.BooksReader;
import com.mycompany.serializers.stringformat.readers.PublishersReader;
import com.mycompany.serializers.stringformat.validators.Validator;
import com.mycompany.serializers.stringformat.writers.AuthorsWriterInTextFile;
import com.mycompany.serializers.stringformat.writers.BooksWriterInTextFile;
import com.mycompany.serializers.stringformat.writers.PublishersWriterInFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TextFormatSerializer implements Serializer {

    static private final String AUTHOR_MODEL = "Authors";
    static private final String BOOK_MODEL = "Books";
    static private final String PUBLISHER_MODEL = "Publishers";

    public void serializeObjects(List<Publisher> publishers, String fileWithObjects) throws FileNotFoundException {
        try (PrintWriter fileWriter = new PrintWriter(new File(fileWithObjects))) {

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

            fileWriter.print(AUTHOR_MODEL);
            AuthorsWriterInTextFile.writeAuthors(fileWriter, authorEntities);

            fileWriter.println();
            fileWriter.print(BOOK_MODEL);
            BooksWriterInTextFile.writeBooks(fileWriter, bookEntities);

            fileWriter.println();
            fileWriter.print(PUBLISHER_MODEL);
            PublishersWriterInFile.writePublishers(fileWriter, publisherEntities);

            fileWriter.close();
        }
    }


    @Override
    public List<Publisher> deserializeObject(String fileWithObjects) throws IOException {

        File file = new File(fileWithObjects);

        if (!Validator.validateContent(file)) {
            System.out.println("Error in " + fileWithObjects);
            return null;
        }

        AuthorsReader authorsReader = new AuthorsReader();
        List<AuthorEntity> authorsEntities = authorsReader.read(file);
        List<Author> authors = AuthorsRestorator.getListOfAuthors(authorsEntities);

        BooksReader booksReader = new BooksReader();
        List<BookEntity> bookEntities = booksReader.read(file);
        List<Book> books = BooksRestorator.getListOfBooks(bookEntities, authors, authorsEntities);

        PublishersReader publishersReader = new PublishersReader();
        List<PublisherEntity> publisherEntities = publishersReader.read(file);

        return PublishersRestorator.getListOfPublishers(publisherEntities, bookEntities, books);
    }

}

