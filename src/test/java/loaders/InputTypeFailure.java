package loaders;

import algorithms.LoadingAbstractAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import structures.MatrixInput;

import java.io.FileInputStream;

public class InputTypeFailure extends AbstractLoader{
    private LoadingAbstractAlgorithm algo = new LoadingAbstractAlgorithm();
    private Gson gson = new Gson();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();
    private JsonParser parser = new JsonParser();
    private String FIFOPIPE = "/tmp/algoout";

    public JsonObject GenerateInput() {
        MatrixInput inputObj = new MatrixInput(new Float[]{0.25f, 0.15f}, new Float[]{0.12f, -0.15f});
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "json");
        object.add("data", gson.toJsonTree(inputObj));
        return object;
    }

    public JsonObject GenerateOutput() {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("message", "Missing required field in JSON input: name");
        return expectedResponse;
    }


    public JsonObject run() throws Exception {
        Handler handler = new Handler<>(algo);
        prepareInput(request);
        FileInputStream inputStream = new FileInputStream(FIFOPIPE);
        handler.serve();
        byte[] fifoBytes = IOUtils.toByteArray(inputStream);
        String rawData = new String(fifoBytes);
        JsonObject actualResponse = parser.parse(rawData).getAsJsonObject();
        return actualResponse;
    }
}
