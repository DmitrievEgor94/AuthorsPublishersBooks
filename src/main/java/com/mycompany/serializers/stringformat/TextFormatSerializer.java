package com.mycompany.serializers.stringformat;

import com.mycompany.entities.*;
import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.Publisher;
import com.mycompany.serializers.Serializer;
import com.mycompany.serializers.stringformat.readers.AuthorsReader;
import com.mycompany.serializers.stringformat.readers.BooksReader;
import com.mycompany.serializers.stringformat.readers.ObjectsReader;
import com.mycompany.serializers.stringformat.readers.PublishersReader;
import com.mycompany.serializers.stringformat.validators.FileNotValidException;
import com.mycompany.serializers.stringformat.writers.AuthorsWriterInTextFile;
import com.mycompany.serializers.stringformat.writers.BooksWriterInTextFile;
import com.mycompany.serializers.stringformat.writers.ObjectsWriter;
import com.mycompany.serializers.stringformat.writers.PublishersWriterInFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TextFormatSerializer implements Serializer {

    static private final String AUTHOR_MODEL = "Authors";
    static private final String BOOK_MODEL = "Books";
    static private final String PUBLISHER_MODEL = "Publishers";

    static private final ObjectsWriter<AuthorEntity> AUTHORS_WRITER = new AuthorsWriterInTextFile();
    static private final ObjectsWriter<BookEntity> BOOKS_WRITER = new BooksWriterInTextFile();
    static private final ObjectsWriter<PublisherEntity> PUBLISHERS_WRITER = new PublishersWriterInFile();

    static private final ObjectsReader<AuthorEntity> AUTHORS_READER = new AuthorsReader();
    static private final ObjectsReader<BookEntity> BOOKS_READER = new BooksReader();
    static private final ObjectsReader<PublisherEntity> PUBLISHERS_READER = new PublishersReader();


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

            List<AuthorEntity> authorEntities = EntitiesCreator.getListAuthorEntities(authors);
            List<BookEntity> bookEntities = EntitiesCreator.getListBookEntities(books, authorEntities);
            List<PublisherEntity> publisherEntities = EntitiesCreator.getListPublisherEntities(publishers, bookEntities);

            fileWriter.print(AUTHOR_MODEL);
            AUTHORS_WRITER.write(fileWriter, authorEntities);

            fileWriter.println();
            fileWriter.print(BOOK_MODEL);
            BOOKS_WRITER.write(fileWriter, bookEntities);

            fileWriter.println();
            fileWriter.print(PUBLISHER_MODEL);
            PUBLISHERS_WRITER.write(fileWriter, publisherEntities);

            fileWriter.close();
        }
    }

    @Override
    public List<Publisher> deserializeObject(String fileWithObjects) throws IOException, FileNotValidException {

        File file = new File(fileWithObjects);

        try (Scanner scanner = new Scanner(file)) {

            List<AuthorEntity> authorsEntities = AUTHORS_READER.read(scanner);
            List<Author> authors = ModelsRestorator.getListOfAuthors(authorsEntities);

            List<Integer> availableAuthorsId = authorsEntities.stream()
                    .map(AuthorEntity::getId)
                    .collect(Collectors.toList());

            List<BookEntity> bookEntities = BOOKS_READER.read(scanner);

            for (BookEntity bookEntity : bookEntities) {
                if (Collections.indexOfSubList(availableAuthorsId, bookEntity.getAuthorsId()) == -1) {
                    throw new FileNotValidException("Authors id for book were changed in file!");
                }
            }

            List<Book> books = ModelsRestorator.getListOfBooks(bookEntities, authors, authorsEntities);

            List<Integer> availableBooksId = bookEntities.stream()
                    .map(BookEntity::getId)
                    .collect(Collectors.toList());

            List<PublisherEntity> publisherEntities = PUBLISHERS_READER.read(scanner);

            for (PublisherEntity publisherEntity : publisherEntities) {
                if (Collections.indexOfSubList(availableBooksId, publisherEntity.getBooksId()) == -1) {
                    throw new FileNotValidException("Books id for publisher were changed in file!");
                }
            }

            return ModelsRestorator.getListOfPublishers(publisherEntities, bookEntities, books);
        }
    }

}

