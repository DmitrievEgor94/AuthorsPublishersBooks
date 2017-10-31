package com.mycompany.serializer.validators_of_objects;

import com.mycompany.serializer.readers_of_objects.BracketsFinder;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

class BooksValidatorInFile {

    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";

    static boolean validateBooks(String booksContent, String authorsContent) {
        List<Integer> openBracketPositions = BracketsFinder.getBracketPositions(booksContent, CLASS_OPEN_BRACKET);
        List<Integer> closeBracketPositions = BracketsFinder.getBracketPositions(booksContent, CLASS_CLOSE_BRACKET);

        List<Integer> authorsAvailableId = GetterAvailableIdList.getIdList(authorsContent);

        for (int i = 0; i < openBracketPositions.size(); i++) {
            int openBracketPosition = openBracketPositions.get(i);
            int closeBracketPosition = closeBracketPositions.get(i);

            String contentOfClass = booksContent.substring(openBracketPosition + 1, closeBracketPosition);

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

            String publicationDateAndValue = scanner.nextLine();
            if (!FieldValidator.validateDate(publicationDateAndValue)) {
                return false;
            }

            String authorsIdAndValues = scanner.nextLine();
            if (!FieldValidator.validateListOfId(authorsIdAndValues, authorsAvailableId)) {
                return false;
            }
        }

        return true;
    }
}
