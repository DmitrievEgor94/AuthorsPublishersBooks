package com.mycompany.serializers.stringformat.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AuthorsValidator implements ObjectsValidator {

    private static final String AUTHOR_BLOCK_NAME = "Authors";

    @Override
    public boolean areObjectsValid(File file) throws FileNotFoundException {

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.contains(AUTHOR_BLOCK_NAME)) {
                    break;
                }
            }

            return scanner.hasNext() && validateAuthors(scanner);
        }
    }

    private boolean validateAuthors(Scanner scanner) {

        while (scanner.hasNext()) {

            String line = scanner.nextLine();

            if (line.contains(LIST_CLOSE_BRACKET)) {
                return true;
            }

            if (line.contains(CLASS_OPEN_BRACKET)) {
                if (!validateAuthor(scanner)) {
                    return false;
                }
            }
        }

        return false;
    }

    private boolean validateAuthor(Scanner scanner) {

        FieldValidator fieldValidator = new FieldValidator();

        String idAndValue = scanner.nextLine();
        if (!fieldValidator.validateId(idAndValue)) {
            System.out.println("Bad author id in file!");

            return false;
        }

        String nameAndValue = scanner.nextLine();
        if (!fieldValidator.checkNumberOfTokens(nameAndValue)) {
            System.out.println("No name of author in file!");
            return false;
        }

        String birthDayAndValue = scanner.nextLine();
        if (!fieldValidator.validateDate(birthDayAndValue)) {
            System.out.println("Bad day of birthday in file!");
            return false;
        }

        String dayOfDeathValue = scanner.nextLine();
        if (!fieldValidator.validateDayOfDeath(dayOfDeathValue)) {
            System.out.println("Bad day of death in file!");
            return false;
        }

        String sexAndValue = scanner.nextLine();
        if (!fieldValidator.checkSexOfAuthor(sexAndValue)) {
            System.out.println("Bad sex in file!");
            return false;
        }

        return true;
    }
}
