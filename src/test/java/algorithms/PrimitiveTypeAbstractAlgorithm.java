package algorithms;

import com.algorithmia.development.AbstractAlgorithm;

public class PrimitiveTypeAbstractAlgorithm extends AbstractAlgorithm<Float, String> {
    public String apply(Float input) throws RuntimeException {
        return "Hello, the number is " + input;
    }
}
