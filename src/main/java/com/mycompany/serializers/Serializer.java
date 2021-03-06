package com.mycompany.serializers;

import com.mycompany.models.Publisher;
import com.mycompany.serializers.stringformat.validators.FileNotValidException;

import java.io.IOException;
import java.util.List;

public interface Serializer {
    void serializeObjects(List<Publisher> publishers, String fileWithObjects) throws IOException;

    List<Publisher> deserializeObject(String fileWithObjects) throws IOException, FileNotValidException;
}
