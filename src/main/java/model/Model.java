package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Model {
    private final String[] matrixWithColors;

    private final int numberOfMatrixColumns;
    private final int numberOfMatrixLines;

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
}
