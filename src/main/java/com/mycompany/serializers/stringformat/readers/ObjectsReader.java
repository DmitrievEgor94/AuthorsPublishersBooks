package com.mycompany.serializers.stringformat.readers;

import com.mycompany.serializers.stringformat.validators.FieldValidator;
import com.mycompany.serializers.stringformat.validators.FileNotValidException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public interface ObjectsReader<T> {

    String CLASS_OPEN_BRACKET = "{";

    String LIST_CLOSE_BRACKET = "]";

    String DELIMITER_BETWEEN_FIELD_VALUE = ":";

    int POSITION_OF_VALUE_TOKEN = 1;

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    FieldValidator FIELD_VALIDATOR = new FieldValidator();

    List<T> read(Scanner scanner) throws FileNotValidException;

}
