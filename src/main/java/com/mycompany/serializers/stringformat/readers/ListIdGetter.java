package com.mycompany.serializers.stringformat.readers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ListIdGetter {
    static List<Integer> getIdList(String contentWithId) {
        List<Integer> listId = new ArrayList<>();

        Scanner scanner = new Scanner(contentWithId);

        scanner.next();

        while (scanner.hasNextInt())
            listId.add(scanner.nextInt());

        return listId;
    }
}
