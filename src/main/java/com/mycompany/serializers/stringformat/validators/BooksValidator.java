package com.mycompany.serializers.stringformat.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class BooksValidator implements ObjectsValidator {

    private static final String AUTHOR_BLOCK_NAME = "Authors";

    private static final String BOOKS_BLOCK_NAME = "Books";

    @Override
    public boolean areObjectsValid(File file) throws FileNotFoundException {

        try (Scanner scanner = new Scanner(file)) {

            List<Integer> authorsId = null;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.contains(AUTHOR_BLOCK_NAME)) {
                    GetterAvailableIdList getterAvailableIdList = new GetterAvailableIdList();

                    authorsId = getterAvailableIdList.getIdList(scanner);

                    break;
                }
            }

            if (authorsId != null) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();

                    if (line.contains(BOOKS_BLOCK_NAME)) {
                        break;
                    }

                }
            }

            return scanner.hasNext() && validateBooks(scanner, authorsId);
        }
    }


    private boolean validateBooks(Scanner scanner, List<Integer> authorsId) {

        while (scanner.hasNext()) {

            String line = scanner.nextLine();

            if (line.contains(LIST_CLOSE_BRACKET)) {
                return true;
            }

            if (line.contains(CLASS_OPEN_BRACKET)) {
                if (!validateBook(scanner, authorsId)) {
                    return false;
                }
            }
        }

        return false;
    }

    private boolean validateBook(Scanner scanner, List<Integer> authorsId) {

        FieldValidator fieldValidator = new FieldValidator();

        String idAndValue = scanner.nextLine();
        if (!fieldValidator.validateId(idAndValue)) {
            System.out.println("Bad book id in file!");
            return false;
        }

        String nameAndValue = scanner.nextLine();
        if (!fieldValidator.checkNumberOfTokens(nameAndValue)) {
            System.out.println("No title for book in file!");
            return false;
        }

        String publicationDateAndValue = scanner.nextLine();
        if (!fieldValidator.validateDate(publicationDateAndValue)) {
            System.out.println("Bad publication day for book in file!");
            return false;
        }

        String authorsIdAndValues = scanner.nextLine();
        if (!fieldValidator.validateListOfId(authorsIdAndValues, authorsId)) {
            System.out.println("Bad authorsId for book in file!");
            return false;
        }

        return true;
    }
}
