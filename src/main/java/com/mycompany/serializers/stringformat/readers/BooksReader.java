package com.mycompany.serializers.stringformat.readers;

import com.mycompany.entities.BookEntity;
import com.mycompany.serializers.stringformat.validators.FileNotValidException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BooksReader implements ObjectsReader<BookEntity> {

    private static final String BOOKS_BLOCK_NAME = "Books";

    public List<BookEntity> read(Scanner scanner) throws FileNotValidException {

        List<BookEntity> bookEntities = new ArrayList<>();

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

        return bookEntities;
    }

    private BookEntity getBookEntity(Scanner scanner) throws FileNotValidException {

        String idAndValue = scanner.nextLine();
        if (!FIELD_VALIDATOR.validateId(idAndValue)) {
            throw new FileNotValidException("Bad book id in file!");
        }
        int id = Integer.parseInt(idAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim());

        String titleAndValue = scanner.nextLine();
        if (!FIELD_VALIDATOR.checkNumberOfTokens(titleAndValue)) {
            throw new FileNotValidException("No title for book in file!");
        }
        String title = titleAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();

        String publicationDateAndValue = scanner.nextLine();
        if (!FIELD_VALIDATOR.validateDate(publicationDateAndValue)) {
            throw new FileNotValidException("Bad publication day for book in file!");
        }
        String publicationDayString = publicationDateAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();
        LocalDate publicationDate = LocalDate.parse(publicationDayString, FORMATTER);

        String authorsIdAndValues = scanner.nextLine();
        if (!FIELD_VALIDATOR.validateListOfId(authorsIdAndValues)) {
            throw new FileNotValidException("No authors for book specified! in file!");
        }
        List<Integer> authorsId = ListIdGetter.getIdList(authorsIdAndValues);

        return new BookEntity(id, title, publicationDate, authorsId);
    }

}