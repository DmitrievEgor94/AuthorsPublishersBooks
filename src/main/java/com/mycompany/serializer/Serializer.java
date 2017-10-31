package com.mycompany.serializer;

import com.mycompany.books_authors_publishers.Author;
import com.mycompany.books_authors_publishers.Book;
import com.mycompany.books_authors_publishers.OriginalModelsContainer;
import com.mycompany.books_authors_publishers.Publisher;

import java.io.IOException;
import java.util.List;

public interface Serializer {
    void serializeObjects(List<Author> authors, List<Book> books, List<Publisher> publishers, String fileWithObjects) throws IOException;

    OriginalModelsContainer deserializeObject(String fileWithObjects) throws IOException, ClassNotFoundException;
}
