package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import geometry.Dimension;

import java.io.IOException;

import static util.FileUtils.deserializeFrom;

public class Model {
    private final String[] matrixWithColors;
    private final Dimension dimension;

    public static final String PROJECTILE_MODEL_FILEPATH = "model/projectile.json";

    public Model(
            @JsonProperty("matrixWithColors") String[] matrixWithColors,
            @JsonProperty("columns") int numberOfMatrixColumns,
            @JsonProperty("lines") int numberOfMatrixLines
    ) {
        this.matrixWithColors = matrixWithColors;
        this.dimension = new Dimension(numberOfMatrixColumns, numberOfMatrixLines);
    }

    public String[] getMatrix() {
        return matrixWithColors;
    }

    public int getWidth() {
        return dimension.getWidth();
    }

    public int getHeight() {
        return dimension.getHeight();
    }

    public Dimension getDimension() {
        return dimension;
    }

    public static Model createModelFrom(String filePath) throws IOException {
        return deserializeFrom(filePath, Model.class);
    }
}
