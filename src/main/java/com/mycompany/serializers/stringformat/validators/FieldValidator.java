package com.mycompany.serializers.stringformat.validators;

import com.mycompany.models.Author;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

class FieldValidator {
    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";
    private static final int NUMBER_OF_NEEDED_TOKENS = 2;
    private static final int POSITION_OF_VALUE_TOKEN = 1;

    static private final String ABSENT_DEATH_DATE = "-";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    boolean validateId(String idAndValue) {

        String[] tokens = idAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE);

        if (tokens.length != NUMBER_OF_NEEDED_TOKENS) {
            return false;
        }

        try {
            Integer.parseInt(tokens[POSITION_OF_VALUE_TOKEN].trim());
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    boolean validateDate(String dateFieldAndValue) {

        String[] tokens = dateFieldAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE);

        if (tokens.length != NUMBER_OF_NEEDED_TOKENS) return false;

        String date = tokens[POSITION_OF_VALUE_TOKEN].trim();

        try {
            LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

    boolean validateDayOfDeath(String dayAndValue) {

        String[] tokens = dayAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE);

        if (tokens.length != NUMBER_OF_NEEDED_TOKENS) return false;

        String date = tokens[POSITION_OF_VALUE_TOKEN].trim();

        if (date.equals(ABSENT_DEATH_DATE)) return true;

        try {
            LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }


    boolean checkNumberOfTokens(String string) {

        String[] tokens = string.split(DELIMITER_BETWEEN_FIELD_VALUE);

        return tokens.length == NUMBER_OF_NEEDED_TOKENS;
    }

    boolean validateListOfId(String idAndValues, List<Integer> availableId) {

        String[] tokens = idAndValues.split(DELIMITER_BETWEEN_FIELD_VALUE);

        if (tokens.length < NUMBER_OF_NEEDED_TOKENS) return false;

        Scanner scanner = new Scanner(tokens[POSITION_OF_VALUE_TOKEN]);

        while (scanner.hasNextInt()) {
            int id = scanner.nextInt();

            if (!availableId.contains(id)) {
                return false;
            }
        }

        return true;
    }

    boolean checkSexOfAuthor(String sexAndValue) {

        String[] tokens = sexAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE);

        if (tokens.length != 2) {
            return false;
        }

        try {
            Author.Sex.valueOf(tokens[POSITION_OF_VALUE_TOKEN].trim());
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
