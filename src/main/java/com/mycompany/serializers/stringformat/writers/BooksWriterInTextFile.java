package com.mycompany.serializers.stringformat.writers;

import com.mycompany.entities.BookEntity;

import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BooksWriterInTextFile {
    private static final String LIST_OPEN_BRACKET = "[";
    private static final String LIST_CLOSE_BRACKET = "]";
    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";

    private static final String ID_FIELD = "Id";
    private static final String TITLE_FIELD = "Title";
    private static final String PUBLICATION_DATE_FIELD = "PublicationDate";
    private static final String AUTHORS_ID_FIELD = "AuthorId";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void writeBooks(PrintWriter file, List<BookEntity> books) {
        if (file == null) return;

        file.println(LIST_OPEN_BRACKET);

        if (books != null) {
            for (BookEntity book : books) {
                file.println(CLASS_OPEN_BRACKET);

                String bookId = fieldAndValue(ID_FIELD, String.valueOf(book.getId()));
                file.println(bookId);

                String bookTitle = fieldAndValue(TITLE_FIELD, book.getTitle());
                file.println(bookTitle);

                String publicationDate = book.getPublicationDate().format(FORMATTER);
                String bookPublicationDate = fieldAndValue(PUBLICATION_DATE_FIELD, publicationDate);
                file.println(bookPublicationDate);

                String bookAuthorsId = listFieldAndValues(AUTHORS_ID_FIELD, book.getAuthorsId());
                file.print(bookAuthorsId);

                file.println();
                file.println(CLASS_CLOSE_BRACKET);
            }
        }

        file.println(LIST_CLOSE_BRACKET);
    }

    private static String fieldAndValue(String field, String value) {
        return String.format("  %s: %s", field, value);
    }

    private static String listFieldAndValues(String field, List<Integer> authorsId) {
        StringBuilder stringBuilder = new StringBuilder();

        String authorsIdDelimiter = " ";

        for (Integer integer : authorsId) {
            stringBuilder.append(integer).append(authorsIdDelimiter);
        }

        return fieldAndValue(field, String.valueOf(stringBuilder));
    }
}
