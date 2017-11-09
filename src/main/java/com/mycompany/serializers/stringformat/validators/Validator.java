package com.mycompany.serializers.stringformat.validators;

import com.mycompany.serializers.stringformat.readers.BracketsFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Validator {
    private static final String LIST_OPEN_BRACKET = "[";
    private static final String LIST_CLOSE_BRACKET = "]";
    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";

    private static final int ORDER_OF_AUTHORS_BLOCK = 0;
    private static final int ORDER_OF_BOOKS_BLOCK = 1;
    private static final int ORDER_OF_PUBLISHERS_BLOCK = 2;

    private static final int OFFSET_FROM_OPEN_BRACKET = 1;


    public static boolean validateContent(String content) {
        List<Integer> openBracketsPositions = BracketsFinder.getBracketPositions(content, LIST_OPEN_BRACKET);
        List<Integer> closeBracketsPositions = BracketsFinder.getBracketPositions(content, LIST_CLOSE_BRACKET);
        if (openBracketsPositions.size() != closeBracketsPositions.size() || closeBracketsPositions.size() != 3) {
            return false;
        }

        if (!areBracketsValid(content)) {
            return false;
        }

        int listAuthorsContentBeginning = openBracketsPositions.get(ORDER_OF_AUTHORS_BLOCK) + OFFSET_FROM_OPEN_BRACKET;
        int listAuthorsContentEnding = closeBracketsPositions.get(ORDER_OF_AUTHORS_BLOCK);
        String listAuthorsContent = content.substring(listAuthorsContentBeginning, listAuthorsContentEnding);

        if (!AuthorsValidator.validate(listAuthorsContent)) {
            return false;
        }

        int listBooksContentBeginning = openBracketsPositions.get(ORDER_OF_BOOKS_BLOCK) + OFFSET_FROM_OPEN_BRACKET;
        int listBooksContentEnding = closeBracketsPositions.get(ORDER_OF_BOOKS_BLOCK);
        String listBooksContent = content.substring(listBooksContentBeginning, listBooksContentEnding);

        if (!BooksValidator.validate(listBooksContent, listAuthorsContent)) {
            return false;

        }

        int listPublishersContentBeginning = openBracketsPositions.get(ORDER_OF_PUBLISHERS_BLOCK) + OFFSET_FROM_OPEN_BRACKET;
        int listPublishersContentEnding = closeBracketsPositions.get(ORDER_OF_PUBLISHERS_BLOCK);
        String listPublishersContent = content.substring(listPublishersContentBeginning, listPublishersContentEnding);

        return PublishersValidator.validate(listPublishersContent, listBooksContent);
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

