package structures;

public class MatrixInput {
    public TwoWideMatrix matrix;

    public MatrixInput(Float[] array1, Float[] array2) {
        TwoWideMatrix matrix = new TwoWideMatrix(array1.length);
        matrix.setTop(array1);
        matrix.setBottom(array2);
        this.matrix = matrix;
    }
}