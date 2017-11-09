package com.mycompany.serializers.stringformat.validators;

import com.mycompany.serializers.stringformat.readers.BracketsFinder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AuthorsValidator {

    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";
    static private final String ABSENT_DEATH_DATE = "-";

    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";
    private static final int NUMBER_OF_NEEDED_TOKENS = 2;
    private static final int POSITION_OF_VALUE_TOKEN = 1;

    private static final int OFFSET_FROM_OPEN_BRACKET = 1;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static boolean validate(String content) {
        List<Integer> openBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_OPEN_BRACKET);
        List<Integer> closeBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_CLOSE_BRACKET);

        for (int i = 0; i < openBracketPositions.size(); i++) {
            int openBracketPosition = openBracketPositions.get(i);
            int closeBracketPosition = closeBracketPositions.get(i);

            String contentOfClass = content.substring(openBracketPosition + OFFSET_FROM_OPEN_BRACKET, closeBracketPosition);

            Scanner scanner = new Scanner(contentOfClass);
            scanner.nextLine();

            String idAndValue = scanner.nextLine();
            if (!FieldValidator.validateId(idAndValue)) {
                return false;
            }

            String nameAndValue = scanner.nextLine();
            if (!FieldValidator.checkNumberOfTokens(nameAndValue)) {
                return false;
            }

            String birthDayAndValue = scanner.nextLine();
            if (!FieldValidator.validateDate(birthDayAndValue)) {
                return false;
            }

            String dayOfDeathValue = scanner.nextLine();
            if (!validateDayOfDeath(dayOfDeathValue)) {
                return false;
            }

            String sexAndValue = scanner.nextLine();
            if (!FieldValidator.checkNumberOfTokens(sexAndValue)) {
                return false;
            }
        }

        return true;
    }


    static private boolean validateDayOfDeath(String dayAndValue) {
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

}
