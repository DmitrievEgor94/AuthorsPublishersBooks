package com.mycompany.serializers.stringformat.readers;

import com.mycompany.entities.PublisherEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PublishersReader {

    private static final String CLASS_OPEN_BRACKET = "{";

    private static final String LIST_CLOSE_BRACKET = "]";

    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";

    private static final int POSITION_OF_VALUE_TOKEN = 1;

    private static final String PUBLISHERS_BLOCK_NAME = "Publishers";

    public List<PublisherEntity> read(File file) throws FileNotFoundException {

        List<PublisherEntity> publisherEntities = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.contains(PUBLISHERS_BLOCK_NAME)) {
                    break;
                }
            }

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.contains(CLASS_OPEN_BRACKET)) {
                    publisherEntities.add(getPublisherEntity(scanner));
                }

                if (line.contains(LIST_CLOSE_BRACKET)) {
                    break;
                }
            }
        }

        return publisherEntities;
    }

    private PublisherEntity getPublisherEntity(Scanner scanner) {

        String nameAndValue = scanner.nextLine();
        String name = nameAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();

        String booksIdList = scanner.nextLine();
        List<Integer> booksId = ListIdGetter.getIdList(booksIdList);

        return new PublisherEntity(name, booksId);
    }
}
