package com.mycompany.serializer.readers_of_objects;

import com.mycompany.books_authors_publishers.Author;
import com.mycompany.books_authors_publishers.enteties_for_serializing.AuthorEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AuthorsReader {
    private static final String CLASS_OPEN_BRACKET = "{";
    private static final String CLASS_CLOSE_BRACKET = "}";
    static private final String ABSENT_DEATH_DATE = "-";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";

    private static final int POSITION_OF_VALUE_TOKEN = 1;

    public static List<AuthorEntity> readAuthors(String content) {
        List<AuthorEntity> authorEntities = new ArrayList<>();

        List<Integer> openBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_OPEN_BRACKET);
        List<Integer> closeBracketPositions = BracketsFinder.getBracketPositions(content, CLASS_CLOSE_BRACKET);

        for (int i = 0; i < openBracketPositions.size(); i++) {
            int openBracketPosition = openBracketPositions.get(i);
            int closeBracketPosition = closeBracketPositions.get(i);

            String contentOfClass = content.substring(openBracketPosition + 1, closeBracketPosition);

            Scanner scanner = new Scanner(contentOfClass);

            scanner.nextLine();

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

            authorEntities.add(new AuthorEntity(id, name, dayOfBirthday, dayOfDeath, sex));
        }

        return authorEntities;
    }
}
