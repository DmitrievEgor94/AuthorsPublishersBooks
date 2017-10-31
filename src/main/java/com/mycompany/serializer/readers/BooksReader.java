package com.mycompany.serializer.readers;

import com.mycompany.books_authors_publishers.entities.BookEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BooksReader {
    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";

    private static final int POSITION_OF_VALUE_TOKEN = 1;

    public static List<BookEntity> readBooks(String content) {
        List<BookEntity> bookEntities = new ArrayList<>();

        List<Integer> openBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_OPEN_BRACKET);
        List<Integer> closeBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_CLOSE_BRACKET);

        for (int i = 0; i < openBracketPositions.size(); i++) {
            int openBracketPosition = openBracketPositions.get(i);
            int closeBracketPosition = closeBracketPositions.get(i);

            String contentOfClass = content.substring(openBracketPosition + 1, closeBracketPosition);

            Scanner scanner = new Scanner(contentOfClass);

            scanner.nextLine();

            String idAndValue = scanner.nextLine();
            int id = Integer.parseInt(idAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim());

            String titleAndValue = scanner.nextLine();
            String title = titleAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();

            String publicationDateAndValue = scanner.nextLine();
            String publicationDayString = publicationDateAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();
            LocalDate publicationDate = LocalDate.parse(publicationDayString, FORMATTER);

            String authorsIdAndValues = scanner.nextLine();
            List<Integer> authorsId = ListIdGetter.getIdList(authorsIdAndValues);

            bookEntities.add(new BookEntity(id, title, publicationDate, authorsId));
        }
        return bookEntities;
    }
}