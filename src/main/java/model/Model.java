package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

import static util.FileUtils.deserializeFrom;

public class Model {
    private final String[] matrixWithColors;
    private final int numberOfMatrixColumns;
    private final int numberOfMatrixLines;

    public static final String PROJECTILE_MODEL_FILEPATH = "model/projectile.json";

    public Model(
            @JsonProperty("matrixWithColors") String[] matrixWithColors,
            @JsonProperty("columns") int numberOfMatrixColumns,
            @JsonProperty("lines") int numberOfMatrixLines
    ) {
        this.matrixWithColors = matrixWithColors;
        this.numberOfMatrixColumns = numberOfMatrixColumns;
        this.numberOfMatrixLines = numberOfMatrixLines;
    }

    public String[] getMatrix() {
        return matrixWithColors;
    }

    public int getNumberOfColumns() {
        return numberOfMatrixColumns;
    }

    public int getNumberOfLines() {
        return numberOfMatrixLines;
    }

    public static Model createModelFrom(String filePath) throws IOException {
        return deserializeFrom(filePath, Model.class);
    }
}
