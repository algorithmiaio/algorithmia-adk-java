package algorithms;

import com.algorithmia.development.AbstractAlgorithm;
import structures.*;


public class MatrixAbstractAlgorithm extends AbstractAlgorithm<MatrixInput, MatrixOutput> {

    public MatrixOutput apply(MatrixInput input) throws RuntimeException {
        if (input.matrix.dimCheck()) {
            Float[] sums = new Float[input.matrix.getWidth()];
            Float[] arrayA = input.matrix.top;
            Float[] arrayB = input.matrix.bottom;
            for (int i = 0; i < input.matrix.getWidth(); i++) {
                sums[i] = arrayA[i] + arrayB[i];
            }
            return new MatrixOutput(sums);
        } else throw new RuntimeException("matrix dimension are not the same shape.");
    }
}
