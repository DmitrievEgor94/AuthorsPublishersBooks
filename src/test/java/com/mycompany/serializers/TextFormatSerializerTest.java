package com.mycompany.serializers;

import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.Publisher;
import com.mycompany.serializers.stringformat.TextFormatSerializer;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class TextFormatSerializerTest {

    private static final String LIST_OPEN_BRACKET = "[";
    private static final String LIST_CLOSE_BRACKET = "]";

    private List<Author> authors;
    private List<Book> books;
    private List<Publisher> publishers;
    private TextFormatSerializer textFormatSerializer;

    private static final String TEST_FILE_WITH_OBJECTS = Serializer.class.getResource("TestSerializedObjects.txt").getPath();
    private static String checkString;

    private static final String GENERATED_FILE_WITH_OBJECTS = "GeneratedFileWithObjects.txt";
    private static String generatedString;

    public TextFormatSerializerTest() throws FileNotFoundException {
        authors = new ArrayList<>();
        books = new ArrayList<>();
        publishers = new ArrayList<>();

        textFormatSerializer = new TextFormatSerializer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        authors.add(new Author("Egor", LocalDate.parse("21.12.1974", formatter), null, Author.Sex.MALE));
        authors.add(new Author("John", LocalDate.parse("10.12.1923", formatter), LocalDate.parse("05.06.1984", formatter), Author.Sex.MALE));

        books.add(new Book("DayOfz", LocalDate.parse("15.02.2000", formatter), authors));
        books.add(new Book("SayHello", LocalDate.parse("30.06.2004", formatter), authors));

        publishers.add(new Publisher("Paragon", books));
        publishers.add(new Publisher("SweetHome", books));

        try (Scanner scanner = new Scanner(new File(TEST_FILE_WITH_OBJECTS))) {
            checkString = scanner.useDelimiter("\\Z").next();
        }

        textFormatSerializer.serializeObjects(authors, books, publishers, GENERATED_FILE_WITH_OBJECTS);

        try (Scanner scanner = new Scanner(new File(TEST_FILE_WITH_OBJECTS))) {
            generatedString = scanner.useDelimiter("\\Z").next();
        }
    }

    @Test
    public void testSerializationAuthors() throws Exception {
        int beginning = checkString.indexOf(LIST_OPEN_BRACKET);
        int ending = checkString.indexOf(LIST_CLOSE_BRACKET);

        String authorsCheckBlock = checkString.substring(beginning, ending);
        String authorsGeneratedBlock = generatedString.substring(beginning, ending);

        assertEquals(authorsCheckBlock, authorsGeneratedBlock);
    }

    @Test
    public void testSerializationBooks() throws Exception {
        int offset = checkString.indexOf(LIST_CLOSE_BRACKET);

        int beginning = checkString.indexOf(LIST_OPEN_BRACKET, offset + 1);
        int ending = checkString.indexOf(LIST_CLOSE_BRACKET, offset + 1);

        String booksCheckBlock = checkString.substring(beginning, ending);
        String booksGeneratedBlock = generatedString.substring(beginning, ending);

        assertEquals(booksCheckBlock, booksGeneratedBlock);
    }

    @Test
    public void testSerializationPublishers() throws Exception {
        int beginning = checkString.lastIndexOf(LIST_OPEN_BRACKET);
        int ending = checkString.lastIndexOf(LIST_CLOSE_BRACKET);

        String publishersCheckBlock = checkString.substring(beginning, ending);
        String publishersGeneratedBlock = generatedString.substring(beginning, ending);

        assertEquals(publishersCheckBlock, publishersGeneratedBlock);
    }

    @Test
    public void testDeserializerAuthors() throws Exception {
        assertEquals(authors, textFormatSerializer.deserializeObject(TEST_FILE_WITH_OBJECTS).getAuthors());
    }

    @Test
    public void testDeserializerBooks() throws Exception {
        assertEquals(books, textFormatSerializer.deserializeObject(TEST_FILE_WITH_OBJECTS).getBooks());
    }

    @Test
    public void testDeserializerPublishers() throws Exception {
        assertEquals(publishers, textFormatSerializer.deserializeObject(TEST_FILE_WITH_OBJECTS).getPublishers());
    }
}
