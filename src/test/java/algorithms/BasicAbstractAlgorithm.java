package algorithms;

import com.algorithmia.development.AbstractAlgorithm;

public class BasicAbstractAlgorithm extends AbstractAlgorithm<String, String>{
    public String apply(String input) {
        return "Hello " + input;
    }
}
