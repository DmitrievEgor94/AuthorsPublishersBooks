package com.mycompany.serializer.readers_of_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListIdGetter {
    static List<Integer> getIdList(String contentWithId) {
        List<Integer> authorsId = new ArrayList<>();

        Scanner scanner = new Scanner(contentWithId);

        scanner.next();

        while (scanner.hasNextInt())
            authorsId.add(scanner.nextInt());

        return authorsId;
    }
}
