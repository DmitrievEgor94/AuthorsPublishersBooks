package com.mycompany.serializer.validators;

import com.mycompany.serializer.readers.BracketsFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Validator {
    private static final String LIST_OPEN_BRACKET = "[";
    private static final String LIST_CLOSE_BRACKET = "]";
    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";


    public static boolean validateContent(String content) {
        List<Integer> openBracketsPositions = BracketsFinder.getBracketPositions(content, LIST_OPEN_BRACKET);
        List<Integer> closeBracketsPositions = BracketsFinder.getBracketPositions(content, LIST_CLOSE_BRACKET);
        if (openBracketsPositions.size() != closeBracketsPositions.size() || closeBracketsPositions.size() != 3) {
            return false;
        }

        if (!areBracketsValid(content)) {
            return false;
        }

        int listAuthorsContentBeginning = openBracketsPositions.get(0) + 1;
        int listAuthorsContentEnding = closeBracketsPositions.get(0);
        String listAuthorsContent = content.substring(listAuthorsContentBeginning, listAuthorsContentEnding);

        if (!AuthorsValidator.validateAuthors(listAuthorsContent)) {
            return false;
        }

        int listBooksContentBeginning = openBracketsPositions.get(1) + 1;
        int listBooksContentEnding = closeBracketsPositions.get(1);
        String listBooksContent = content.substring(listBooksContentBeginning, listBooksContentEnding);

        if (!BooksValidator.validateBooks(listBooksContent, listAuthorsContent)) {
            return false;

        }

        return true;
    }

    private static boolean areBracketsValid(String content) {
        List<Integer> openBracketsPositions = BracketsFinder.getBracketPositions(content, LIST_OPEN_BRACKET);
        List<Integer> closeBracketsPositions = BracketsFinder.getBracketPositions(content, LIST_CLOSE_BRACKET);

        if (openBracketsPositions.size() != closeBracketsPositions.size() || closeBracketsPositions.size() != 3) {
            return false;
        }

        List<Character> openBrackets = new ArrayList<>();
        List<Character> closeBrackets = new ArrayList<>();

        openBrackets.add(LIST_OPEN_BRACKET.charAt(0));
        openBrackets.add(CLASS_OPEN_BRACKET.charAt(0));

        closeBrackets.add(LIST_CLOSE_BRACKET.charAt(0));
        closeBrackets.add(CLASS_CLOSE_BRACKET.charAt(0));

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < content.length(); i++) {
            char currentCharacter = content.charAt(i);

            if (openBrackets.contains(currentCharacter))
                stack.push(currentCharacter);

            if (closeBrackets.contains(currentCharacter)) {
                char openBracket = stack.pop();
                if (openBracket != openBrackets.get(closeBrackets.indexOf(currentCharacter))) return false;
            }
        }

        return stack.empty();
    }

}

