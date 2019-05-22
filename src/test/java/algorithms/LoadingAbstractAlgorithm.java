package algorithms;

import com.algorithmia.development.AbstractAlgorithm;
import structures.*;

import java.util.HashMap;


public class LoadingAbstractAlgorithm extends AbstractAlgorithm<LoadingInput, String> {

    private HashMap<String, String> context = new HashMap<>();


    public String apply(LoadingInput input) throws RuntimeException {
        if (context != null && context.containsKey("local_file")) {
            return "Hello " + input.name + " you are " + input.age +
                    " years old, and your model file is downloaded here " + context.get("local_file");
        }
        throw new RuntimeException("We need to serve DownloadModel before Apply.");
    }

    public void load() throws RuntimeException {
        context.put("local_file", "/tmp/somefile");
    }
}