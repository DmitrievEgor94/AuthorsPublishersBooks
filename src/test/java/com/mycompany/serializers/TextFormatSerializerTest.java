package com.mycompany.serializers;

import com.mycompany.serializers.stringformat.TextFormatSerializer;

public class TextFormatSerializerTest extends TestSerializer {

    private static String CHECK_FILE_NAME = TestSerializer.class.getResource("testStringSerializedObjects.txt").getPath();
    private static Serializer serializer = new TextFormatSerializer();

    public TextFormatSerializerTest() {
        super(serializer, CHECK_FILE_NAME);
    }
}
