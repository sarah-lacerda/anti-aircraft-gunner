package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private FileUtils() {}

    public static <T> T deserializeFrom(String filePath, Class<T> targetClass) throws IOException {
        return OBJECT_MAPPER.readValue(getFileFromResourceAsStream(filePath), targetClass);
    }

    public static InputStream getFileFromResourceAsStream(String filePath) {
        InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("Error while accessing resource, file not found! " + filePath);
        } else {
            return inputStream;
        }
    }
}
