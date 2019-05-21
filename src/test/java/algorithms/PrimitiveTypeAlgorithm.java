package algorithms;

import com.algorithmia.development.AlgorithmInterface;

public class PrimitiveTypeAlgorithm implements AlgorithmInterface<Float, String> {
    public String apply(Float input) throws RuntimeException {
        return "Hello, the number is " + input;
    }
}
