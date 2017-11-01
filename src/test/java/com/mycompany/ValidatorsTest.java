package com.mycompany;

import com.mycompany.serializer.Serializer;
import com.mycompany.serializer.validators.AuthorsValidator;
import com.mycompany.serializer.validators.BooksValidator;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class ValidatorsTest {

    private static final String LIST_OPEN_BRACKET = "[";
    private static final String LIST_CLOSE_BRACKET = "]";

    private static final String TEST_FILE_WITH_OBJECTS = Serializer.class.getResource("BadSerializingObjects.txt").getPath();
    private String checkString;

    public ValidatorsTest() throws Exception {
        try (Scanner scanner = new Scanner(new File(TEST_FILE_WITH_OBJECTS))) {
            checkString = scanner.useDelimiter("\\Z").next();
        }
    }

    @Test
    public void authorsValidatorTest() {
        int beginning = checkString.indexOf(LIST_OPEN_BRACKET);
        int ending = checkString.indexOf(LIST_CLOSE_BRACKET);

        String authorsCheckBlock = checkString.substring(beginning, ending);

        assertEquals(true, AuthorsValidator.validate(authorsCheckBlock));
    }

    @Test
    public void booksValidatorTest() {
        int offset = checkString.indexOf(LIST_CLOSE_BRACKET);

        int beginning = checkString.indexOf(LIST_OPEN_BRACKET, offset + 1);
        int ending = checkString.indexOf(LIST_CLOSE_BRACKET, offset + 1);

        int beginningAuthorBlock = checkString.indexOf(LIST_OPEN_BRACKET);
        int endingAuthorBlock = checkString.indexOf(LIST_CLOSE_BRACKET);

        String authorsCheckBlock = checkString.substring(beginningAuthorBlock, endingAuthorBlock);

        String booksCheckBlock = checkString.substring(beginning, ending);

        assertEquals(false, BooksValidator.validate(booksCheckBlock, authorsCheckBlock));
    }

    @Test
    public void publishersValidatorTest() {
        int offset = checkString.indexOf(LIST_CLOSE_BRACKET);

        int beginningBooksContent = checkString.indexOf(LIST_OPEN_BRACKET, offset + 1);
        int endingBooksContent = checkString.indexOf(LIST_CLOSE_BRACKET, offset + 1);

        String booksCheckBlock = checkString.substring(beginningBooksContent, endingBooksContent);

        int beginning = checkString.lastIndexOf(LIST_OPEN_BRACKET);
        int ending = checkString.lastIndexOf(LIST_CLOSE_BRACKET);

        String publishersCheckBlock = checkString.substring(beginning, ending);

        assertEquals(false, BooksValidator.validate(publishersCheckBlock, booksCheckBlock));
    }
}
