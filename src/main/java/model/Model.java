package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Model {
    private final String[] matrixWithColors;

    private final int numberOfMatrixColumns;

    public Model(
            @JsonProperty("matrixWithColors") String[] matrixWithColors,
            @JsonProperty("columns") int numberOfMatrixColumns
    ) {
        this.matrixWithColors = matrixWithColors;
        this.numberOfMatrixColumns = numberOfMatrixColumns;
    }

    public String[] getMatrix() {
        return matrixWithColors;
    }

    public int getNumberOfColumns() {
        return numberOfMatrixColumns;
    }
}
