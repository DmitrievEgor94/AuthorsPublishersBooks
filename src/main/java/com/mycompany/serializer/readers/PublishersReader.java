package com.mycompany.serializer.readers;

import com.mycompany.books_authors_publishers.entities.PublisherEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PublishersReader {
    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";

    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";

    private static final int POSITION_OF_VALUE_TOKEN = 1;

    public static List<PublisherEntity> readBooks(String content) {
        List<PublisherEntity> publisherEntities = new ArrayList<>();

        List<Integer> openBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_OPEN_BRACKET);
        List<Integer> closeBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_CLOSE_BRACKET);

        for (int i = 0; i < openBracketPositions.size(); i++) {
            int openBracketPosition = openBracketPositions.get(i);
            int closeBracketPosition = closeBracketPositions.get(i);

            String contentOfClass = content.substring(openBracketPosition + 1, closeBracketPosition);

            Scanner scanner = new Scanner(contentOfClass);

            scanner.nextLine();

            String nameAndValue = scanner.nextLine();
            String name = nameAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();

            String booksIdList = scanner.nextLine();
            List<Integer> booksId = ListIdGetter.getIdList(booksIdList);

            publisherEntities.add(new PublisherEntity(name, booksId));
        }

        return publisherEntities;
    }
}
