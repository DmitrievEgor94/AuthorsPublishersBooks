package com.mycompany.serializers.stringformat.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

class FieldValidator {
    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";
    private static final int NUMBER_OF_NEEDED_TOKENS = 2;
    private static final int POSITION_OF_VALUE_TOKEN = 1;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    static boolean validateId(String idAndValue) {
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

    static boolean validateDate(String dateFieldAndValue) {
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


    static boolean checkNumberOfTokens(String string) {
        String[] tokens = string.split(DELIMITER_BETWEEN_FIELD_VALUE);

        return tokens.length == NUMBER_OF_NEEDED_TOKENS;
    }

    static boolean validateListOfId(String idAndValues, List<Integer> availableId) {
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
}
