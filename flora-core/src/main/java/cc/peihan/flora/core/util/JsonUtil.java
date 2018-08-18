package cc.peihan.flora.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class JsonUtil {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }


    public static <T> T read(String json, Class<T> clazz) {
        try {
            return json != null ? objectMapper.readValue(json, clazz) : null;
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T read(String json, TypeReference typeReference) {
        try {
            return json != null ? objectMapper.readValue(json, typeReference) : null;
        } catch (IOException e) {
            return null;
        }
    }

    public static String writeAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(String.format("Fail to convert Object [%s] to json string", object.getClass().getName()), e);
        }
    }

}
