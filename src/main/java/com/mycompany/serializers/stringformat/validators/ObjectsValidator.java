package com.mycompany.serializers.stringformat.validators;

import java.io.File;
import java.io.FileNotFoundException;

public interface ObjectsValidator {

    String CLASS_OPEN_BRACKET = "{";

    String LIST_CLOSE_BRACKET = "]";

    boolean areObjectsValid(File file) throws FileNotFoundException;
}
