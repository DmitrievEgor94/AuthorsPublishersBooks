package com.mycompany.serializers.stringformat.writers;

import com.mycompany.entities.PublisherEntity;

import java.io.PrintWriter;
import java.util.List;

public class PublishersWriterInFile implements ObjectsWriter<PublisherEntity> {

    private static final String NAME_FIELD = "Name";
    private static final String BOOKS_ID_FIELD = "BooksID";

    public void write(PrintWriter file, List<PublisherEntity> publishers) {
        if (file == null) return;

        file.println(LIST_OPEN_BRACKET);

        if (publishers != null) {
            for (PublisherEntity publisher : publishers) {
                file.println(CLASS_OPEN_BRACKET);

                String publisherName = fieldAndValue(NAME_FIELD, publisher.getName());
                file.println(publisherName);

                String publisherBooksId = listFieldAndValues(BOOKS_ID_FIELD, publisher.getBooksId());
                file.println(publisherBooksId);

                file.println(CLASS_CLOSE_BRACKET);
            }
        }

        file.println(LIST_CLOSE_BRACKET);
    }

    private static String fieldAndValue(String field, String value) {
        return String.format("  %s: %s", field, value);
    }

    private static String listFieldAndValues(String field, List<Integer> booksId) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer integer : booksId) {
            stringBuilder.append(integer).append(" ");
        }

        return fieldAndValue(field, stringBuilder.toString());
    }
}
