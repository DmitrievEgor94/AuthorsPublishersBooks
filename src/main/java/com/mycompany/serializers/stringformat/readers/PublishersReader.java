package com.mycompany.serializers.stringformat.readers;

import com.mycompany.entities.PublisherEntity;
import com.mycompany.serializers.stringformat.validators.FileNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PublishersReader implements ObjectsReader<PublisherEntity> {

    private static final String PUBLISHERS_BLOCK_NAME = "Publishers";

    public List<PublisherEntity> read(Scanner scanner) throws FileNotValidException {

        List<PublisherEntity> publisherEntities = new ArrayList<>();

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

        return publisherEntities;
    }

    private PublisherEntity getPublisherEntity(Scanner scanner) throws FileNotValidException {

        String nameAndValue = scanner.nextLine();
        if (!FIELD_VALIDATOR.checkNumberOfTokens(nameAndValue)) {
            throw new FileNotValidException("No name for publisher in file!");
        }
        String name = nameAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();

        String booksIdList = scanner.nextLine();
        if (!FIELD_VALIDATOR.validateListOfId(booksIdList)) {
            throw new FileNotValidException("No books for publisher specified in file!");
        }
        List<Integer> booksId = ListIdGetter.getIdList(booksIdList);

        return new PublisherEntity(name, booksId);
    }
}
