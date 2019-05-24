package algorithms;

import com.algorithmia.development.AbstractAlgorithm;

public class BinaryAbstractAlgorithm extends AbstractAlgorithm<byte[], String> {
     public  String apply(byte[] input) {
        return new String(input);
    }
}
