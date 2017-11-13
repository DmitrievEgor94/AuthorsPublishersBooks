package com.mycompany.serializers.stringformat.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Validator {
    private static final String LIST_OPEN_BRACKET = "[";
    private static final String LIST_CLOSE_BRACKET = "]";
    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";

    private static final int CORRECT_NUMBER_OF_BRACKETS = 3;

    public static boolean validateContent(File file) throws FileNotFoundException {

        if (!areBracketsValid(file)) {
            System.out.println("Brackets are not valid in file!");
            return false;
        }

        ObjectsValidator objectsValidator = new AuthorsValidator();

        if (!objectsValidator.areObjectsValid(file)) {
            return false;
        }

        objectsValidator = new BooksValidator();

        if (!objectsValidator.areObjectsValid(file)) {
            return false;

        }

        objectsValidator = new PublishersValidator();
        return objectsValidator.areObjectsValid(file);
    }

    private static boolean areBracketsValid(File file) throws FileNotFoundException {
        List<Integer> openBracketsPositions = BracketsFinder.getBracketPositions(file, LIST_OPEN_BRACKET);
        List<Integer> closeBracketsPositions = BracketsFinder.getBracketPositions(file, LIST_CLOSE_BRACKET);

        if (openBracketsPositions.size() != closeBracketsPositions.size() || closeBracketsPositions.size() != CORRECT_NUMBER_OF_BRACKETS) {
            return false;
        }

        List<Character> openBrackets = new ArrayList<>();
        List<Character> closeBrackets = new ArrayList<>();

        openBrackets.add(LIST_OPEN_BRACKET.charAt(0));
        openBrackets.add(CLASS_OPEN_BRACKET.charAt(0));

        closeBrackets.add(LIST_CLOSE_BRACKET.charAt(0));
        closeBrackets.add(CLASS_CLOSE_BRACKET.charAt(0));

        Stack<Character> stack = new Stack<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {

                String line = scanner.nextLine();

                for (int i = 0; i < line.length(); i++) {
                    char currentCharacter = line.charAt(i);

                    if (openBrackets.contains(currentCharacter))
                        stack.push(currentCharacter);

                    if (closeBrackets.contains(currentCharacter)) {
                        char openBracket = stack.pop();
                        if (openBracket != openBrackets.get(closeBrackets.indexOf(currentCharacter))) return false;
                    }
                }
            }
        }

        return stack.empty();
    }

}

