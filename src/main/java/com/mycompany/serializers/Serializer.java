package com.mycompany.serializers;

import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.OriginalModelsContainer;
import com.mycompany.models.Publisher;

import java.io.IOException;
import java.util.List;

public interface Serializer {
    void serializeObjects(List<Author> authors, List<Book> books, List<Publisher> publishers, String fileWithObjects) throws IOException;

    OriginalModelsContainer deserializeObject(String fileWithObjects) throws IOException;
}
