package com.mycompany.serializers.stringformat.readers;

import com.mycompany.entities.AuthorEntity;
import com.mycompany.models.Author;
import com.mycompany.serializers.stringformat.validators.FileNotValidException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorsReader implements ObjectsReader<AuthorEntity> {

    static private final String ABSENT_DEATH_DATE = "-";

    private static final String AUTHOR_BLOCK_NAME = "Authors";

    public List<AuthorEntity> read(Scanner scanner) throws FileNotValidException {

        List<AuthorEntity> authorEntities = new ArrayList<>();

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

        return authorEntities;
    }

    private AuthorEntity getAuthorEntity(Scanner scanner) throws FileNotValidException {

        String idAndValue = scanner.nextLine();
        if (!FIELD_VALIDATOR.validateId(idAndValue)) {
            throw new FileNotValidException("Bad author id in file!");
        }
        int id = Integer.parseInt(idAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim());

        String nameAndValue = scanner.nextLine();
        if (!FIELD_VALIDATOR.checkNumberOfTokens(nameAndValue)) {
            throw new FileNotValidException("No name of author in file!");
        }
        String name = nameAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();

        String dayOfBirthdayAndValue = scanner.nextLine();
        if (!FIELD_VALIDATOR.validateDate(dayOfBirthdayAndValue)) {
            throw new FileNotValidException("Bad day of birthday in file!");
        }
        String dayOfBirthdayString = dayOfBirthdayAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();
        LocalDate dayOfBirthday = LocalDate.parse(dayOfBirthdayString, FORMATTER);

        String dayOfDeathAndValue = scanner.nextLine();
        if (!FIELD_VALIDATOR.validateDayOfDeath(dayOfDeathAndValue)) {
            throw new FileNotValidException("Bad day of death in file!");
        }
        LocalDate dayOfDeath = null;
        String dayOfDeathString = dayOfDeathAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[POSITION_OF_VALUE_TOKEN].trim();
        if (!dayOfDeathString.equals(ABSENT_DEATH_DATE)) {
            dayOfDeath = LocalDate.parse(dayOfDeathString, FORMATTER);
        }

        String sexAndValue = scanner.nextLine();
        if (!FIELD_VALIDATOR.checkSexOfAuthor(sexAndValue)) {
            throw new FileNotValidException("Bad sex in file!");
        }
        String sexString = sexAndValue.split(DELIMITER_BETWEEN_FIELD_VALUE)[1].trim();
        Author.Sex sex = Author.Sex.valueOf(sexString);

        return new AuthorEntity(id, name, dayOfBirthday, dayOfDeath, sex);
    }
}
