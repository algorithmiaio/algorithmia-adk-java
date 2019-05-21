package algorithms;

import com.algorithmia.development.AlgorithmInterface;

public class BinaryAlgorithm implements AlgorithmInterface<byte[], String> {
    public String apply(byte[] input) {
        return new String(input);
    }
}
