package algorithms;

import com.algorithmia.development.AlgorithmInterface;

public class BasicAlgorithm implements AlgorithmInterface<String, String> {
    public String apply(String input) {
        return "Hello " + input;
    }
}
