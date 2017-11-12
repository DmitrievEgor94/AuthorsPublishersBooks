package com.mycompany.serializers.stringformat.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GetterAvailableIdList {

    private static final String LIST_CLOSE_BRACKET = "]";
    private static final String CLASS_OPEN_BRACKET = "{";

    List<Integer> getIdList(Scanner scanner) {

        List<Integer> listWithId = new ArrayList<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            if (line.contains(CLASS_OPEN_BRACKET)) {
                scanner.next();
                int id = scanner.nextInt();
                listWithId.add(id);
            }

            if (line.contains(LIST_CLOSE_BRACKET)) {
                return listWithId;
            }
        }

        return null;
    }
}
