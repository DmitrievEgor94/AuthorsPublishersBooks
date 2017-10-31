package com.mycompany;

import com.mycompany.books_authors_publishers.OriginalModelsContainer;
import com.mycompany.books_authors_publishers.Author;
import com.mycompany.books_authors_publishers.Book;
import com.mycompany.books_authors_publishers.Publisher;
import com.mycompany.serializer.JavaSerializer;
import com.mycompany.serializer.Serializer;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

//import com.mycompany.serializer.JavaSerializer;
//import com.mycompany.serializer.TextFormatSerializer;

public class SerializersTest {
    private List<Author> authors;
    private List<Book> books;
    private List<Publisher> publishers;

    public SerializersTest() throws FileNotFoundException {
        authors = new ArrayList<>();
        books = new ArrayList<>();
        publishers = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        authors.add(new Author("Egor", LocalDate.parse("21.12.1974", formatter), null, Author.Sex.MALE));
        authors.add(new Author("John", LocalDate.parse("10.12.1923", formatter), LocalDate.parse("05.06.1984", formatter), Author.Sex.MALE));

        books.add(new Book("DayOfz", LocalDate.parse("15.02.2000", formatter), authors));
        books.add(new Book("SayHello", LocalDate.parse("30.06.2004", formatter), authors));

        publishers.add(new Publisher("Paragon", books));
        publishers.add(new Publisher("SweetHome", books));
    }

    @Test
    public void testSerializers() throws Exception {
        //  Serializer textSerializer = new TextFormatSerializer();
        Serializer javaSerializer = new JavaSerializer();

        String rootFolder = Serializer.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        String javaSerializerFile = rootFolder + "/JavaSerializedObjects.txt";
        ;

        javaSerializer.serializeObjects(authors, books, publishers, javaSerializerFile);

        OriginalModelsContainer originalModelsContainer = javaSerializer.deserializeObject(javaSerializerFile);
        assertEquals(originalModelsContainer.getPublishers(),publishers);

        // useSerializer(textSerializer, authorsTextSerializerFile, booksTextSerializerFile,publishersTextSerializerFile);
        // useSerializer(javaSerializer, authorsJavaSerializerFile,booksJavaSerializerFile,publishersJavaSerializerFile);
    }


//   @Test
//    public void getAuthorsTest() {
//        assertEquals(authors, AuthorsRestorator.getListOfAuthors(authorsEntities));
//    }

//    @Test
//    public void getBooksTest() {
//        assertEquals(books, BooksRestorator.getListOfBooks(booksEntities, authors, authorsEntities));
//    }

//    @Test
//    public void getPublishersTest() {
//        assertEquals(publishers, PublishersRestorator.getListOfPublishers(publishersEntities, booksEntities, books));
//    }


}