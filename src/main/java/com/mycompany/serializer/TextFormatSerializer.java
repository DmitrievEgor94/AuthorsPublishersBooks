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
import com.mycompany.serializer.readers.AuthorsReader;
import com.mycompany.serializer.readers.BooksReader;
import com.mycompany.serializer.readers.BracketsFinder;
import com.mycompany.serializer.readers.PublishersReader;
import com.mycompany.serializer.validators.Validator;
import com.mycompany.serializer.writers.AuthorsWriterInTextFile;
import com.mycompany.serializer.writers.BooksWriterInTextFile;
import com.mycompany.serializer.writers.PublishersWriterInFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class TextFormatSerializer implements Serializer {

    static private final String AUTHOR_MODEL = "Authors";
    static private final String BOOK_MODEL = "Books";
    static private final String PUBLISHER_MODEL = "Publishers";
    private static final String LIST_OPEN_BRACKET = "[";
    private static final String LIST_CLOSE_BRACKET = "]";

    private static final int ORDER_OF_AUTHORS_BLOCK = 0;
    private static final int ORDER_OF_BOOKS_BLOCK = 1;
    private static final int ORDER_OF_PUBLISHERS_BLOCK = 2;

    private static final int OFFSET_FROM_OPEN_BRACKET = 1;

    public void serializeObjects(List<Author> authors, List<Book> books, List<Publisher> publishers, String fileWithObjects) throws FileNotFoundException {
        try (PrintWriter fileWriter = new PrintWriter(new File(fileWithObjects))) {

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
    public OriginalModelsContainer deserializeObject(String fileWithObjects) throws IOException, ClassNotFoundException {
        try (Scanner scanner = new Scanner(new File(fileWithObjects))) {
            String content = scanner.useDelimiter("\\Z").next();

            if (!Validator.validateContent(content)) throw new ClassNotFoundException("Invalid data in file!");

            List<Integer> openBracketsPositions = BracketsFinder.getBracketPositions(content, LIST_OPEN_BRACKET);
            List<Integer> closeBracketsPositions = BracketsFinder.getBracketPositions(content, LIST_CLOSE_BRACKET);


            int listAuthorsContentBeginning = openBracketsPositions.get(ORDER_OF_AUTHORS_BLOCK) + OFFSET_FROM_OPEN_BRACKET;
            int listAuthorsContentEnding = closeBracketsPositions.get(ORDER_OF_AUTHORS_BLOCK);
            String listAuthorsContent = content.substring(listAuthorsContentBeginning, listAuthorsContentEnding);
            List<AuthorEntity> authorsEntities = AuthorsReader.readAuthors(listAuthorsContent);
            List<Author> authors = AuthorsRestorator.getListOfAuthors(authorsEntities);

            int listBooksContentBeginning = openBracketsPositions.get(ORDER_OF_BOOKS_BLOCK) + OFFSET_FROM_OPEN_BRACKET;
            int listBooksContentEnding = closeBracketsPositions.get(ORDER_OF_BOOKS_BLOCK);
            String listBooksContent = content.substring(listBooksContentBeginning, listBooksContentEnding);
            List<BookEntity> bookEntities = BooksReader.readBooks(listBooksContent);
            List<Book> books = BooksRestorator.getListOfBooks(bookEntities, authors, authorsEntities);


            int listPublishersContentBeginning = openBracketsPositions.get(ORDER_OF_PUBLISHERS_BLOCK) + OFFSET_FROM_OPEN_BRACKET;
            int listPublishersContentEnding = closeBracketsPositions.get(ORDER_OF_PUBLISHERS_BLOCK);
            String listPublishersContent = content.substring(listPublishersContentBeginning, listPublishersContentEnding);
            List<PublisherEntity> publisherEntities = PublishersReader.readBooks(listPublishersContent);
            List<Publisher> publishers = PublishersRestorator.getListOfPublishers(publisherEntities, bookEntities, books);

            return new OriginalModelsContainer(authors, books, publishers);
        }
    }

}

