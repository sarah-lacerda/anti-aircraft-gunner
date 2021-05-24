package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Model;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Model getModelFrom(String filePath) throws IOException {
        return objectMapper.readValue(getFileFromResourceAsStream(filePath), Model.class);
    }

    private static InputStream getFileFromResourceAsStream(String filePath) {
        InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found! " + filePath);
        } else {
            return inputStream;
        }

    }
}
