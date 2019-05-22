package algorithms;

import com.algorithmia.development.AbstractAlgorithm;

public class ThrowsExceptionAbstractAlgorithm extends AbstractAlgorithm<String, String> {
    public String apply(String input){
        throw new RuntimeException("This is an exception.");
    }
}
