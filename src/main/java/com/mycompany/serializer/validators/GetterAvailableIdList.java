package com.mycompany.serializer.validators;

import com.mycompany.serializer.readers.BracketsFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GetterAvailableIdList {

    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";

    static List<Integer> getIdList(String content) {
        List<Integer> idList = new ArrayList<>();

        List<Integer> openBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_OPEN_BRACKET);
        List<Integer> closeBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_CLOSE_BRACKET);

        for (int i = 0; i < openBracketPositions.size(); i++) {
            int openBracketPosition = openBracketPositions.get(i);
            int closeBracketPosition = closeBracketPositions.get(i);

            String contentOfClass = content.substring(openBracketPosition + 1, closeBracketPosition);

            Scanner scanner = new Scanner(contentOfClass);

            scanner.next();
            int id = scanner.nextInt();

            idList.add(id);
        }

        return idList;
    }
}
