package com.mycompany.serializers;

import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.Publisher;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class TestSerializer {

    private static List<Author> checkAuthors;
    private static List<Book> checkBooks;
    private static List<Publisher> checkPublishers;

    private List<Author> deserializedAuthors;
    private List<Book> deserializedBooks;
    private List<Publisher> deserializedPublishers;

    private Serializer serializer;

    private String checkFileForSerializing;

    private final String CREATED_FILE_FOR_SERIALIZING = "serializedObjects.txt";

    TestSerializer(Serializer serializer, String checkFileForSerializing) {
        this.serializer = serializer;
        this.checkFileForSerializing = checkFileForSerializing;
    }

    @BeforeClass
    public static void init() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        checkAuthors = new ArrayList<>();
        checkBooks = new ArrayList<>();
        checkPublishers = new ArrayList<>();

        checkAuthors.add(new Author("Egor", LocalDate.parse("21.12.1974", formatter), null, Author.Sex.MALE));
        checkAuthors.add(new Author("John", LocalDate.parse("10.12.1923", formatter), LocalDate.parse("05.06.1984", formatter), Author.Sex.MALE));

        checkBooks.add(new Book("DayOfz", LocalDate.parse("15.02.2000", formatter), checkAuthors));
        checkBooks.add(new Book("SayHello", LocalDate.parse("30.06.2004", formatter), checkAuthors));

        checkPublishers.add(new Publisher("Paragon", checkBooks));
        checkPublishers.add(new Publisher("SweetHome", checkBooks));

    }

    @Test
    public void testSerializeObjects() throws IOException {
        serializer.serializeObjects(checkPublishers, CREATED_FILE_FOR_SERIALIZING);

        File createdFile = new File(CREATED_FILE_FOR_SERIALIZING);
        File checkFile = new File(checkFileForSerializing);

        assertTrue("The files differ!", FileUtils.contentEquals(createdFile, checkFile));
    }

    @Before
    public void getDeserializedObjects() throws IOException {
        deserializedPublishers = serializer.deserializeObject(checkFileForSerializing);

        deserializedBooks = deserializedPublishers.stream()
                .map(Publisher::getBooks)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        deserializedAuthors = deserializedBooks.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public void testDeserializeAuthors() throws IOException {
        assertEquals(checkAuthors, deserializedAuthors);
    }

    public void testDeserializeBooks() throws IOException {
        assertEquals(checkBooks, deserializedBooks);
    }

    public void testDeserializePublishers() throws IOException {
        assertEquals(checkPublishers, deserializedPublishers);
    }
}
