package com.mycompany.serializer.writers_of_objects;

import com.mycompany.books_authors_publishers.enteties_for_serializing.PublisherEntity;

import java.io.PrintWriter;
import java.util.List;

public class PublishersWriterInFile {
    private static final String LIST_OPEN_BRACKET = "[";
    private static final String LIST_CLOSE_BRACKET = "]";
    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";

    private static final String NAME_FIELD = "Name";
    private static final String BOOKS_ID_FIELD = "BooksID";

    public static void writePublishers(PrintWriter file, List<PublisherEntity> publishers) {
        if (file== null) return;

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

        return String.format("%s: %s", field, stringBuilder);
    }
}
