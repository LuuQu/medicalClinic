package com.LuuQu.medicalclinic.testHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


public class TestControllerHelper {
    
    public static String getObjectAsString(Object object, ObjectMapper om) {
        ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
        String expectedResult;
        try {
            expectedResult = removeEmptyLines(ow.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return expectedResult;
    }

    public static String removeEmptyLines(String string) {
        return string.replaceAll("\r\n", "")
                .replaceAll(" ", "");
    }
}
