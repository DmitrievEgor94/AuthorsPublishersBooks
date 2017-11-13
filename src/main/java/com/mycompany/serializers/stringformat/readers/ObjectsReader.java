package com.mycompany.serializers.stringformat.readers;

import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public interface ObjectsReader<T> {

    String CLASS_OPEN_BRACKET = "{";

    String LIST_CLOSE_BRACKET = "]";

    String DELIMITER_BETWEEN_FIELD_VALUE = ":";

    int POSITION_OF_VALUE_TOKEN = 1;

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    List<T> read(Scanner scanner);

}
