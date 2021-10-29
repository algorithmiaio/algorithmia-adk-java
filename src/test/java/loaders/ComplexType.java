package loaders;

import algorithms.LoadingAbstractAlgorithm;
import com.algorithmia.development.ADK;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import structures.LoadingInput;

import java.io.FileInputStream;


public class ComplexType extends AbstractLoader{
    private LoadingAbstractAlgorithm algorithm = new LoadingAbstractAlgorithm();
    private Gson gson = new Gson();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();

    public JsonObject GenerateInput() {
        LoadingInput inputObj = new LoadingInput("james", 25);
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "json");
        object.add("data", gson.toJsonTree(inputObj));
        return object;
    }

    public JsonObject GenerateOutput() {
        String outputObj = "Hello james you are 25 years old, and your model file is downloaded here /tmp/somefile";
        JsonObject expectedResponse = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("content_type", "text");
        expectedResponse.add("metadata", metadata);
        expectedResponse.addProperty("result", outputObj);
        return expectedResponse;
    }


    public JsonObject run() throws Exception {
        prepareInput(request);
        ADK algo = new ADK<>(algorithm);
        FileInputStream inputStream = new FileInputStream(FIFOPIPE);
        algo.init();
        byte[] fifoBytes = IOUtils.toByteArray(inputStream);
        String rawData = new String(fifoBytes);
        return parser.parse(rawData).getAsJsonObject();
    }
}
