package com.mycompany.serializers;

public class JavaSerializerTest extends TestSerializer {

    private static String CHECK_FILE_NAME = TestSerializer.class.getResource("testJavaSerializedObjects").getPath();
    private static Serializer serializer = new JavaSerializer();

    public JavaSerializerTest() {
        super(serializer, CHECK_FILE_NAME);
    }
}
