package algorithms;

import com.algorithmia.development.AlgorithmInterface;

public class ThrowsExceptionAlgorithm implements AlgorithmInterface<String, String> {
    public String apply(String input){
        throw new RuntimeException("This is an exception.");
    }
}
