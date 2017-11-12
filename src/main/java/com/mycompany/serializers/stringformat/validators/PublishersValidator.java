package com.mycompany.serializers.stringformat.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

class PublishersValidator implements ObjectsValidator {

    private static final String BOOKS_BLOCK_NAME = "Books";

    private static final String PUBLISHERS_BLOCK_NAME = "Publishers";

    @Override
    public boolean areObjectsValid(File file) throws FileNotFoundException {

        try (Scanner scanner = new Scanner(file)) {

            List<Integer> booksId = null;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.contains(BOOKS_BLOCK_NAME)) {
                    GetterAvailableIdList getterAvailableIdList = new GetterAvailableIdList();

                    booksId = getterAvailableIdList.getIdList(scanner);

                    break;
                }
            }

            if (booksId != null) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();

                    if (line.contains(PUBLISHERS_BLOCK_NAME)) {
                        break;
                    }

                }
            }

            return scanner.hasNext() && validatePublishers(scanner, booksId);
        }
    }

    private boolean validatePublishers(Scanner scanner, List<Integer> booksId) {
        while (scanner.hasNext()) {

            String line = scanner.nextLine();

            if (line.contains(LIST_CLOSE_BRACKET)) {
                return true;
            }

            if (line.contains(CLASS_OPEN_BRACKET)) {
                if (!validatePublisher(scanner, booksId)) {
                    return false;
                }
            }
        }

        return false;
    }

    private boolean validatePublisher(Scanner scanner, List<Integer> booksId) {

        FieldValidator fieldValidator = new FieldValidator();

        String nameAndValue = scanner.nextLine();
        if (!fieldValidator.checkNumberOfTokens(nameAndValue)) {
            System.out.println("No name for publisher in file!");
            return false;
        }

        String booksIdAndValues = scanner.nextLine();
        if (!fieldValidator.validateListOfId(booksIdAndValues, booksId)) {
            System.out.println("Bad booksId for publisher in file!");
            return false;
        }

        return true;
    }
}
