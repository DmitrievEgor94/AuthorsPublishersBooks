package com.mycompany.serializers.stringformat.writers;

import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface ObjectsWriter<T> {

    String LIST_OPEN_BRACKET = "[";
    String LIST_CLOSE_BRACKET = "]";

    String CLASS_OPEN_BRACKET = "{";
    String CLASS_CLOSE_BRACKET = "}";

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    void write(PrintWriter file, List<T> objects);
}
