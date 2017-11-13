package com.mycompany.serializers.stringformat.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BracketsFinder {
     static List<Integer> getBracketPositions(File file, String bracket) throws FileNotFoundException {
        List<Integer> indexBracketPositions = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                int index = line.indexOf(bracket);

                if (index != -1) {
                    indexBracketPositions.add(index);
                }
            }
        }

        return indexBracketPositions;
    }
}
