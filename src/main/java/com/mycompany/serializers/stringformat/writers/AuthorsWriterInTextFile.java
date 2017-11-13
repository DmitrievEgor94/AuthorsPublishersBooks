package com.mycompany.serializers.stringformat.writers;

import com.mycompany.entities.AuthorEntity;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class AuthorsWriterInTextFile implements ObjectsWriter<AuthorEntity> {

    private static final String ID_FIELD = "Id";
    private static final String NAME_FIELD = "Name";
    private static final String DAY_OF_BIRTHDAY_FIELD = "DayOfBirthday";
    private static final String DAY_OF_DEATH_FIELD = "DayOfDeath";
    private static final String SEX_FIELD = "Sex";

    private static final String ABSENT_DEATH_DATE = "-";

    public void write(PrintWriter file, List<AuthorEntity> authors) {
        if (file == null) return;

        file.println(LIST_OPEN_BRACKET);

        if (authors != null) {
            for (AuthorEntity author : authors) {
                file.println(CLASS_OPEN_BRACKET);

                String authorId = fieldAndValue(ID_FIELD, String.valueOf(author.getId()));
                file.println(authorId);

                String authorName = fieldAndValue(NAME_FIELD, author.getName());
                file.println(authorName);

                String dayOfBirthDay = author.getDayOfBirthday().format(FORMATTER);
                String authorDayOfBirthday = fieldAndValue(DAY_OF_BIRTHDAY_FIELD, dayOfBirthDay);
                file.println(authorDayOfBirthday);

                String authorDayOfDeath = dayOfDeathFieldAndValue(DAY_OF_DEATH_FIELD, author.getDayOfDeath());
                file.println(authorDayOfDeath);

                String authorSex = fieldAndValue(SEX_FIELD, String.valueOf(author.getSex()));
                file.println(authorSex);

                file.println(CLASS_CLOSE_BRACKET);
            }
        }

        file.println(LIST_CLOSE_BRACKET);
    }

    private static String fieldAndValue(String field, String value) {
        return String.format("  %s: %s", field, value);
    }

    private static String dayOfDeathFieldAndValue(String field, LocalDate dayOfDeath) {
        if (dayOfDeath == null)
            return fieldAndValue(field, ABSENT_DEATH_DATE);
        else return fieldAndValue(field, dayOfDeath.format(FORMATTER));
    }
}
