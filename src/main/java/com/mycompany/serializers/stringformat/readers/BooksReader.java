package com.mycompany.serializers.stringformat.readers;

import com.mycompany.entities.BookEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BooksReader {

    private static final String CLASS_OPEN_BRACKET = "{";

    private static final String LIST_CLOSE_BRACKET = "]";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";

    private static final int POSITION_OF_VALUE_TOKEN = 1;

    private static final String BOOKS_BLOCK_NAME = "Books";

    public List<BookEntity> read(File file) throws FileNotFoundException {

        List<BookEntity> bookEntities = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.contains(BOOKS_BLOCK_NAME)) {
                    break;
                }
            }

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.contains(CLASS_OPEN_BRACKET)) {
                    bookEntities.add(getBookEntity(scanner));
                }

                if (line.contains(LIST_CLOSE_BRACKET)) {
                    break;
                }

            }
        }

        return bookEntities;
    }

    private BookEntity getBookEntity(Scanner scanner) {

        String idAndValue = scanner.nextLine();
        int id = Integer.parseInt(idAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim());

        String titleAndValue = scanner.nextLine();
        String title = titleAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();

        String publicationDateAndValue = scanner.nextLine();
        String publicationDayString = publicationDateAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();
        LocalDate publicationDate = LocalDate.parse(publicationDayString, FORMATTER);

        String authorsIdAndValues = scanner.nextLine();
        List<Integer> authorsId = ListIdGetter.getIdList(authorsIdAndValues);

        return new BookEntity(id, title, publicationDate, authorsId);
    }

}