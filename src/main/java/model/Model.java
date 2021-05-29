package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import geometry.Dimension;

import java.io.IOException;

import static java.lang.String.*;
import static util.FileUtils.deserializeFrom;

public class Model {
    private final String[] matrixWithColors;
    private final Dimension dimension;

    public static final String PLAYER_MODEL_FILE_PATH = "model/player.json";
    public static final String ROCKET_LAUNCHER_MODEL_FILE_PATH = "model/rocketLauncher.json";
    public static final String PROJECTILE_MODEL_FILEPATH = "model/projectile.json";

    public Model(
            @JsonProperty(required = true, value = "matrixWithColors") String[] matrixWithColors,
            @JsonProperty(required = true, value = "columns") int numberOfMatrixColumns,
            @JsonProperty(required = true, value = "lines") int numberOfMatrixLines
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

    public static Model createModelFrom(String filePath) {
        try {
            return deserializeFrom(filePath, Model.class);
        } catch (IOException e) {
            throw new IllegalStateException(format("Cannot create game model! %s", e.getMessage()));
        }
    }
}
