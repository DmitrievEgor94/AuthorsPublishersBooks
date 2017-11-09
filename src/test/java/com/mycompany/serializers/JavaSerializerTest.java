package com.mycompany.serializers;

import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.OriginalModelsContainer;
import com.mycompany.models.Publisher;
import com.mycompany.models.readers.AuthorsListCreator;
import com.mycompany.models.readers.BooksListCreator;
import com.mycompany.models.readers.PublishersListCreator;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class JavaSerializerTest {
    private List<Author> authors;
    private List<Book> books;
    private List<Publisher> publishers;

    private JavaSerializer javaSerializer;

    private String javaSerializerFile;

    public JavaSerializerTest() throws IOException {
        authors = AuthorsListCreator.getListWithAuthors();
        books = BooksListCreator.getListWithBooks(authors);
        publishers = PublishersListCreator.getListWithPublishers(books);

        javaSerializer = new JavaSerializer();

        String rootFolder = Serializer.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        javaSerializerFile = rootFolder + "/JavaSerializedObjects.txt";

        javaSerializer.serializeObjects(authors, books, publishers, javaSerializerFile);
    }

    @Test
    public void testAuthorsJavaSerializer() throws Exception {
        OriginalModelsContainer originalModelsContainer = javaSerializer.deserializeObject(javaSerializerFile);
        assertEquals(originalModelsContainer.getAuthors(), authors);
    }

    @Test
    public void testBooksJavaSerializer() throws Exception {
        OriginalModelsContainer originalModelsContainer = javaSerializer.deserializeObject(javaSerializerFile);
        assertEquals(originalModelsContainer.getBooks(), books);
    }


    @Test
    public void testPublishersJavaSerializer() throws Exception {
        OriginalModelsContainer originalModelsContainer = javaSerializer.deserializeObject(javaSerializerFile);
        assertEquals(originalModelsContainer.getPublishers(), publishers);
    }
}