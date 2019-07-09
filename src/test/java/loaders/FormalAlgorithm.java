package loaders;

import algorithms.MatrixAbstractAlgorithm;
import com.algorithmia.development.Handler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import structures.MatrixInput;
import structures.MatrixOutput;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class FormalAlgorithm extends AbstractLoader{
    private MatrixAbstractAlgorithm algo = new MatrixAbstractAlgorithm();
    private Gson gson = new Gson();
    private JsonObject request = GenerateInput();
    public JsonObject expectedResponse = GenerateOutput();
    private JsonParser parser = new JsonParser();
    private String FIFOPIPE = "/tmp/algoout";


    public JsonObject GenerateInput() {
        MatrixInput inputObj = new MatrixInput(new Float[]{0.25f, 0.25f, 0.25f}, new Float[]{0.25f, 0.25f, 0.25f});
        gson.toJsonTree(inputObj);
        JsonObject object = new JsonObject();
        object.addProperty("content_type", "json");
        object.add("data", gson.toJsonTree(inputObj));
        return object;
    }

    public JsonObject GenerateOutput() {
        MatrixOutput outputObj = new MatrixOutput(new Float[]{0.5f, 0.5f, 0.5f});
        JsonObject expectedResponse = new JsonObject();
        JsonObject metadata = new JsonObject();
        metadata.addProperty("content_type", "json");
        expectedResponse.add("metadata", metadata);
        expectedResponse.add("result", gson.toJsonTree(outputObj));
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
