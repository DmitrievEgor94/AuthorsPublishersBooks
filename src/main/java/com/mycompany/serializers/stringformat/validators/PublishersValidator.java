package com.mycompany.serializers.stringformat.validators;

import com.mycompany.serializers.stringformat.readers.BracketsFinder;

import java.util.List;
import java.util.Scanner;

class PublishersValidator {

    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";

    private static final int OFFSET_FROM_OPEN_BRACKET = 1;

    static boolean validate(String publishersContent, String booksContent) {

        List<Integer> openBracketPositions = BracketsFinder.getBracketPositions(publishersContent, CLASS_OPEN_BRACKET);
        List<Integer> closeBracketPositions = BracketsFinder.getBracketPositions(publishersContent, CLASS_CLOSE_BRACKET);

        List<Integer> booksAvailableId = GetterAvailableIdList.getIdList(booksContent);

        for (int i = 0; i < openBracketPositions.size(); i++) {
            int openBracketPosition = openBracketPositions.get(i);
            int closeBracketPosition = closeBracketPositions.get(i);

            String contentOfClass = publishersContent.substring(openBracketPosition + OFFSET_FROM_OPEN_BRACKET, closeBracketPosition);

            Scanner scanner = new Scanner(contentOfClass);
            scanner.nextLine();

            String nameAndValue = scanner.nextLine();
            if (!FieldValidator.checkNumberOfTokens(nameAndValue)) {
                return false;
            }

            String booksIdAndValues = scanner.nextLine();
            if (!FieldValidator.validateListOfId(booksIdAndValues, booksAvailableId)) {
                return false;
            }
        }

        return true;
    }
}
