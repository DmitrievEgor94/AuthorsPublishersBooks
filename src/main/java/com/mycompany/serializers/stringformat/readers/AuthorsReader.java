package com.mycompany.serializers.stringformat.readers;

import com.mycompany.entities.AuthorEntity;
import com.mycompany.models.Author;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorsReader {

    private static final String CLASS_OPEN_BRACKET = "{";

    private static final String LIST_CLOSE_BRACKET = "]";

    static private final String ABSENT_DEATH_DATE = "-";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";

    private static final int POSITION_OF_VALUE_TOKEN = 1;

    private static final String AUTHOR_BLOCK_NAME = "Authors";

    public List<AuthorEntity> read(File file) throws FileNotFoundException {

        List<AuthorEntity> authorEntities = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.contains(AUTHOR_BLOCK_NAME)) {
                    break;
                }
            }

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.contains(CLASS_OPEN_BRACKET)) {
                    authorEntities.add(getAuthorEntity(scanner));
                }

                if (line.contains(LIST_CLOSE_BRACKET)) {
                    break;
                }

            }
        }

        return authorEntities;
    }

    private AuthorEntity getAuthorEntity(Scanner scanner) {

        String idAndValue = scanner.nextLine();
        int id = Integer.parseInt(idAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim());

        String nameAndValue = scanner.nextLine();
        String name = nameAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();

        String dayOfBirthdayAndValue = scanner.nextLine();
        String dayOfBirthdayString = dayOfBirthdayAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();
        LocalDate dayOfBirthday = LocalDate.parse(dayOfBirthdayString, FORMATTER);

        String dayOfDeathAndValue = scanner.nextLine();
        LocalDate dayOfDeath = null;
        String dayOfDeathString = dayOfDeathAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();
        if (!dayOfDeathString.equals(ABSENT_DEATH_DATE)) {
            dayOfDeath = LocalDate.parse(dayOfDeathString, FORMATTER);
        }

        String sexAndValue = scanner.nextLine();
        String sexString = sexAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[1].trim();
        Author.Sex sex = Author.Sex.valueOf(sexString);

        return new AuthorEntity(id, name, dayOfBirthday, dayOfDeath, sex);

    }
}
