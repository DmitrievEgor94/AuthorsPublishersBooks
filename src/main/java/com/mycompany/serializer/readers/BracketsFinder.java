package com.mycompany.serializer.readers_of_objects;

import java.util.ArrayList;
import java.util.List;

public class BracketsFinder {
    public static List<Integer> getBracketPositions(String content, String bracket) {
        List<Integer> openBracketPositions = new ArrayList<>();

        for (int index = content.indexOf(bracket); index >= 0;
             index = content.indexOf(bracket, index + 1)) {
            openBracketPositions.add(index);
        }

        return openBracketPositions;
    }
}